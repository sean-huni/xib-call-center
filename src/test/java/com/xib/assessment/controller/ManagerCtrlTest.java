package com.xib.assessment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xib.assessment.dto.ManagerDto;
import com.xib.assessment.service.ManagerService;
import com.xib.assessment.util.ManagerStub;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ManagerCtrlTest {
    @MockBean
    private ManagerService managerService;
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("Find all mangers - HTTP.GET")
    void givenManagerService_whenInvokingRestGetAllManagers_thenReturnManagers() {
        when(managerService.findAllManagers()).thenReturn(ManagerStub.getManagersDto());

        given()
                .contentType("application/json;charset=UTF-8")
                .when()
                .get("/manager")
                .then()
                .body("[0]", Matchers.notNullValue())
                .body("[0].firstName", Matchers.is("John"))
                .body("[0].id", Matchers.nullValue())
                .body("[1]", Matchers.notNullValue())
                .body("[1].firstName", Matchers.is("Alice"))
                .body("[1].lastName", Matchers.is("Wonderland"))
                .body("[1].id", Matchers.nullValue())
                .assertThat()
                .statusCode(200);

        verify(managerService, times(1)).findAllManagers();
    }

    @Test
    @DisplayName("Create new Manager - HTTP.POST")
    void givenManagerService_whenInvokingRestPostCreateNewManager_thenReturnSavedManager() throws Exception {
        ManagerDto savedManager = ManagerStub.getManagerDto();
        ManagerDto unsavedManager = ManagerStub.getManagersDto().stream().findFirst().orElseThrow(Exception::new);
        when(managerService.saveManager(any(ManagerDto.class))).thenReturn(savedManager);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(unsavedManager);

        given()
                .contentType("application/json;charset=UTF-8")
                .body(json)
                .when()
                .post("/manager")
                .then()
                .body("", Matchers.notNullValue())
                .body("firstName", Matchers.is("Terrance"))
                .body("id", Matchers.is(3))
                .body("lastName", Matchers.is("Snipe"))
                .body("email", Matchers.is("terr@test.com"))
                .assertThat()
                .statusCode(200);

        verify(managerService, times(1)).saveManager(unsavedManager);
    }
}