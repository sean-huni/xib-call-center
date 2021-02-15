package com.xib.assessment;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AssessmentApplicationTestMockedSpringContext {
    @InjectMocks
    private SpringApplication springApplication;
    @Mock
    private ConfigurableApplicationContext configurableApplicationContext;

    @Test
    @DisplayName("Load Spring Context Successfully")
    void contextLoads() {


//        when(SpringApplication.run(AssessmentApplication.class,"Test", "Config"))
//                .thenReturn(configurableApplicationContext);
//
//        AssessmentApplication.main(new String[]{"Test", "Config"});
//
//        verify(AssessmentApplication, times(1)).main(new String[]{"Test", "Config"});

    }
}

