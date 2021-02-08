package com.xib.assessment.converter.todto;

import com.xib.assessment.dto.AgentDto;
import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.persistence.model.Agent;
import com.xib.assessment.persistence.model.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.converter.Converter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ToTeamDtoTest {
    @Autowired
    private Converter<Team, TeamDto> toTeamDto;

    @Test
    void givenAgentConverter_whenConvertingFromAgentToAgentDto() {
        Team agentResp = new Team(1L, "Jumbo");

        TeamDto resp = toTeamDto.convert(agentResp);

        assertEquals(1, resp.getId());
        assertEquals("Jumbo", resp.getName());
        assertNull(resp.getAgentDtoCollection());
    }
}