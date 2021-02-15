package com.xib.assessment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AssessmentApplicationTests {
    @Autowired
    private ApplicationContext context;

    @BeforeEach
    void setup() {

    }

    @Test
    @DisplayName("Load Spring Context Successfully")
    void contextLoads() {
        assertNotNull(context);
    }

}
