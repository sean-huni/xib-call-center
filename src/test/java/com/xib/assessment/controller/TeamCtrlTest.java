package com.xib.assessment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xib.assessment.dto.AgentDto;
import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.exception.AgentAlreadyAssignedException;
import com.xib.assessment.exception.AgentNotFoundException;
import com.xib.assessment.exception.ManagedTeamException;
import com.xib.assessment.exception.ManagerAlreadyAssignedException;
import com.xib.assessment.exception.ManagerNotFoundException;
import com.xib.assessment.exception.TeamNotFoundException;
import com.xib.assessment.persistence.model.Team;
import com.xib.assessment.persistence.repo.TeamRepo;
import com.xib.assessment.service.TeamManagementService;
import com.xib.assessment.service.TeamService;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.xib.assessment.util.TestAgentStub.getAgents;
import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TeamCtrlTest {
    @MockBean
    private TeamService teamService;
    @LocalServerPort
    private int port;



    @MockBean
    private TeamManagementService teamManagementService;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }


    @Test
    void findAllTeams() {
        Collection<TeamDto> teams = getAgents().stream().map(AgentDto::getTeamDto).collect(Collectors.toCollection(HashSet::new));
        when(teamService.findAllTeams()).thenReturn(teams);

        given()
                .contentType("application/json;charset=UTF-8")
                .when()
                .get("/team")
                .then()
                .body("[0]", Matchers.notNullValue())
                .body("[0].name", Matchers.is("DC"))
                .body("[0].id", Matchers.is(1))
                .body("[1]", Matchers.notNullValue())
                .body("[1].name", Matchers.is("Marvel"))
                .body("[1].id", Matchers.is(2))
                .assertThat()
                .statusCode(200);

        verify(teamService, times(1)).findAllTeams();
    }

    @Test
    void findTeamById() throws Exception {
        TeamDto team = getAgents().stream().map(AgentDto::getTeamDto).findFirst().orElseThrow(Exception::new);

        when(teamService.findTeamById(1L)).thenReturn(team);

        given()
                .contentType("application/json;charset=UTF-8")
                .when()
                .get("/team/{id}", 1L)
                .then()
                .body("", Matchers.notNullValue())
                .body("name", Matchers.is("DC"))
                .body("id", Matchers.is(1))
                .assertThat()
                .statusCode(200);

        verify(teamService, times(1)).findTeamById(1L);
    }

    @Test
    void createNewTeam() throws Exception {
        TeamDto team = new TeamDto(3L, "Zim");
        when(teamService.saveTeam(team)).thenReturn(team);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(team);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(json)
                .post("/team")
                .then()
                .body("name", Matchers.is("Zim"))
                .body("id", Matchers.is(3))
                .assertThat()
                .statusCode(200).log();

        verify(teamService, times(1)).saveTeam(team);
    }

    @Test
    @DisplayName("Assigning Agent To Team - ThrowTeamNotFoundException")
    void givenTeamController_whenAssigningAgentToTeam_thenThrowTeamNotFoundException() throws Exception {
        when(teamService.assignAgent(2L, 3L)).thenThrow(new TeamNotFoundException("validation.error.notFound.team", 2L));

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put("/team/{id}/agent/{agent-id}", 2L, 3L)
                .then()
                .body("field", Matchers.is("id"))
                .body("rejectedValue", Matchers.is("2"))
                .body("message", Matchers.is("Team-ID: 2 does not exist"))
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value()).log();

        verify(teamService, times(1)).assignAgent(2L, 3L);
    }

    @Test
    @DisplayName("Assigning Agent To Team - AgentNotFoundException")
    void givenTeamController_whenAssigningAgentToTeam_thenThrowAgentNotFoundException() throws Exception {
        when(teamService.assignAgent(2L, 7L)).thenThrow(new AgentNotFoundException("validation.error.notFound.agent", 7L));

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put("/team/{id}/agent/{agent-id}", 2L, 7L)
                .then()
                .body("field", Matchers.is("agent-id"))
                .body("rejectedValue", Matchers.is("7"))
                .body("message", Matchers.is("Agent-ID: 7 does not exist"))
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value()).log();

        verify(teamService, times(1)).assignAgent(2L, 7L);
    }

    @Test
    @DisplayName("Assigning Agent To Team - AgentAlreadyAssignedException")
    void givenTeamController_whenAssigningAgentToTeam_thenThrowAgentAlreadyAssignedException() throws Exception {
        when(teamService.assignAgent(8L, 9L)).thenThrow(new AgentAlreadyAssignedException("validation.error.assigned.agent.team", 9L, 8L));

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put("/team/{id}/agent/{agent-id}", 8L, 9L)
                .then()
                .body("field", Matchers.is("agent-id"))
                .body("rejectedValue", Matchers.is("9"))
                .body("message", Matchers.is("Agent-ID: 9 already assigned to Team-ID: 8"))
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value()).log();

        verify(teamService, times(1)).assignAgent(8L, 9L);
    }

    @Test
    @DisplayName("Assigning Manager To Team - TeamNotFoundException")
    void givenExistingManager_whenAssigningManagerToTeam_thenThrowTeamNotFoundException() throws Exception {
        when(teamManagementService.assignManager(10L, 9L)).thenThrow(new TeamNotFoundException("validation.error.notFound.team", 10L));

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put("/team/{id}/manager/{manager-id}", 10L, 9L)
                .then()
                .body("field", Matchers.is("id"))
                .body("rejectedValue", Matchers.is("10"))
                .body("message", Matchers.is("Team-ID: 10 does not exist"))
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value()).log();

        verify(teamManagementService, times(1)).assignManager(10L, 9L);
    }


    @Test
    @DisplayName("Assigning Manager To Team - ManagerNotFoundException")
    void givenExistingManager_whenAssigningManagerToTeam_thenThrowManagerNotFoundException() throws Exception {
        when(teamManagementService.assignManager(10L, 9L)).thenThrow(new ManagerNotFoundException("validation.error.notFound.manager", 9L));

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put("/team/{id}/manager/{manager-id}", 10L, 9L)
                .then()
                .body("field", Matchers.is("manager-id"))
                .body("rejectedValue", Matchers.is("9"))
                .body("message", Matchers.is("Manager-ID: 9 does not exist"))
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value()).log();

        verify(teamManagementService, times(1)).assignManager(10L, 9L);
    }

    @Test
    @DisplayName("Assigning Manager To Team - ManagedTeamException")
    void givenExistingManager_whenAssigningManagerToTeam_thenThrowManagedTeamException() throws Exception {
        when(teamManagementService.assignManager(10L, 9L)).thenThrow(new ManagedTeamException("validation.error.assigned.managed.teams", 10L, 2));

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put("/team/{id}/manager/{manager-id}", 10L, 9L)
                .then()
                .body("field", Matchers.is("team-id"))
                .body("rejectedValue", Matchers.is("10"))
                .body("message", Matchers.is("Team-ID: 10 already has maximum allowed 2 Managers per team"))
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value()).log();

        verify(teamManagementService, times(1)).assignManager(10L, 9L);
    }

    @Test
    @DisplayName("Assigning Manager To Team - ManagerAlreadyAssignedException")
    void givenExistingManager_whenAssigningManagerToTeam_thenThrowManagerAlreadyAssignedException() throws Exception {
        when(teamManagementService.assignManager(10L, 9L)).thenThrow(new ManagerAlreadyAssignedException("validation.error.assigned.manager.team", 9L, 10L));

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put("/team/{id}/manager/{manager-id}", 10L, 9L)
                .then()
                .body("field", Matchers.is("manager-id"))
                .body("rejectedValue", Matchers.is("9"))
                .body("message", Matchers.is("Manger-ID: 9 already assigned to Team-ID: 10"))
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value()).log();

        verify(teamManagementService, times(1)).assignManager(10L, 9L);
    }

}