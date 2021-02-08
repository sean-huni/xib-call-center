package com.xib.assessment.controller;

import com.xib.assessment.AssessmentApplication;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AssessmentApplication.class)
class HomeCtrlTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("Reroute request to the Swagger-Ui Documentation - HTTP.GET")
    void givenHttpGetReq_whenNavigatingToRoot_thenSwaggerRedirectUri(){
        given()
                .contentType("application/json;charset=UTF-8")
                .when()
                .get("/")
                .then()
                .body(containsString("Swagger UI"))
                .assertThat()
                .statusCode(200);
    }
}