package com.xib.assessment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xib.assessment.dto.AgentDto;
import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.service.TeamService;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

import static com.xib.assessment.util.TestCases.getAgents;
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
                .body("[0].name", Matchers.is("Marvel"))
                .body("[0].id", Matchers.is(2))
                .body("[1]", Matchers.notNullValue())
                .body("[1].name", Matchers.is("DC"))
                .body("[1].id", Matchers.is(1))
                .assertThat()
                .statusCode(200);
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
}