package com.xib.assessment.controller;

import com.xib.assessment.dto.AgentDto;
import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.service.AgentService;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Collection;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AgentCtrlTest {
    @MockBean
    private AgentService agentService;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }


    @Test
    @DisplayName("Find Agent by Id - HTTP.GET")
    void givenAgentCtrl_whenGetAgentById_thenReturnMockedAgent() {
        AgentDto agent = new AgentDto(1L, "Sean", "Huni", "1501246344184", new TeamDto(1L, "DC", null));

        when(agentService.findAgentById(999L)).thenReturn(agent);

        given()
                .contentType("application/json;charset=UTF-8")
                .when()
                .get("agent/{id}", 999)
                .then()
                .body("idNumber", Matchers.equalTo("1501246344184"))
                .body("lastName", Matchers.equalTo("Huni"))
                .assertThat()
                .statusCode(200);
    }

    @Test
    @DisplayName("Find All Agents - HTTP.GET")
    void givenAgentCtrl_whenGetAllAgents_thenReturnAllMockedAgents() {
        when(agentService.findAllAgents()).thenReturn(getAgents());

        given()
                .contentType("application/json;charset=UTF-8")
                .when()
                .get("agent")
                .then()
                .body("[0].idNumber", Matchers.equalTo("1501246344184"))
                .body("[0].lastName", Matchers.equalTo("Huni"))
                .body("", Matchers.hasSize(6))
                .assertThat()
                .statusCode(200);
    }

    @Test
    @DisplayName("Find All Agents with pagination - HTTP.GET")
    void givenAgentCtrl_whenGetAllAgents_withPagination_thenReturnAllMockedAgents() {
        Collection<AgentDto> agents = getAgents();
        agents.removeIf(a -> a.getLastName().contains("ne"));

        when(agentService.findAllAgents(anyInt(), anyInt(), any(), anyString())).thenReturn(agents);

        given()
                .contentType("application/json;charset=UTF-8")
                .when()
                .get("agent?size=3&page=0&field=firstName&sort=DESC")
                .then()
                .body("", Matchers.hasSize(4))
                .assertThat()
                .statusCode(200);
    }


    private static Collection<AgentDto> getAgents(){
        Collection<AgentDto> agents = new ArrayList<>();

        agents.add(new AgentDto(1L, "Sean", "Huni", "1501246344184", new TeamDto(1L, "DC", null)));
        agents.add(new AgentDto(2L, "Bruce", "Banner", "1011125190081", new TeamDto(1L, "DC", null)));
        agents.add(new AgentDto(3L, "Tony", "Stark", "6912115191083", new TeamDto(1L, "DC", null)));
        agents.add(new AgentDto(4L, "Peter", "Parker", "7801115190084", new TeamDto(1L, "DC", null)));
        agents.add(new AgentDto(5L, "Bruce", "Wayne", "6511185190085", new TeamDto(2L, "Marvel", null)));
        agents.add(new AgentDto(6L, "Clark", "Kent", "5905115190086",new TeamDto(2L, "Marvel", null)));

        return agents;
    }
}