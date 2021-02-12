package com.xib.assessment.controller;

import com.xib.assessment.dto.ManagedTeamDto;
import com.xib.assessment.dto.ManagerDto;
import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.service.TeamManagementService;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ManagedTeamsCtrlTest {
    @MockBean
    private TeamManagementService teamManagementService;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @Test
    void findManagedTeamByTeamIdAndManagerId() {
        TeamDto teamDto = new TeamDto(5L, "Drama");
        ManagerDto manager = new ManagerDto(16L, "Dread", "Drock", "dd@gmail.com");
        ManagedTeamDto managedTeamDto = new ManagedTeamDto();
        managedTeamDto.setManager(manager);
        managedTeamDto.setTeam(teamDto);
        managedTeamDto.setId(1L);
        when(teamManagementService.findManagedTeamByTeamIdAndManagerId(5L, 16L)).thenReturn(managedTeamDto);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("team-id", 5)
                .param("manager-id", 16)
                .when()
                .get("managed-team/find")
                .then()
                .body("team", Matchers.notNullValue())
                .body("manager", Matchers.notNullValue())
                .body("team.id", Matchers.equalTo(5))
                .body("manager.id", Matchers.equalTo(16))
                .body("manager.firstName", Matchers.equalTo("Dread"))
                .body("team.name", Matchers.equalTo("Drama"))
                .assertThat()
                .statusCode(HttpStatus.OK.value()).log().body();
    }
}