package com.xib.assessment.converter.tomodel;

import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.persistence.model.Team;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class ToTeamModelTest {
    @Autowired
    private Converter<TeamDto, Team> toTeamDto;

    @BeforeEach
    @DirtiesContext
    void setup() {}

    @Test
    void givenAgentConverter_whenConvertingFromAgentToAgentDto() {
        TeamDto agentResp = new TeamDto(1L, "Jumbo-2");

        Team resp = toTeamDto.convert(agentResp);

        assertEquals(1, resp.getId());
        assertEquals("Jumbo-2", resp.getName());
        assertNull(resp.getAgents());
    }
}
