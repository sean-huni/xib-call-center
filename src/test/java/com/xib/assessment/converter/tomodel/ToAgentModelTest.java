package com.xib.assessment.converter.tomodel;

import com.xib.assessment.dto.AgentDto;
import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.persistence.model.Agent;
import com.xib.assessment.persistence.model.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.converter.Converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ToAgentModelTest {
    @Autowired
    private Converter<AgentDto, Agent> toAgentModel;

    @Test
    void givenAgentConverter_whenConvertingFromAgentToAgentDto() {
        AgentDto agentResp = new AgentDto(123L, "Sean", "Huni", "1501246344184", new TeamDto(1L, "DC", null));

        Agent resp = toAgentModel.convert(agentResp);

        assertEquals(123L, resp.getId());
        assertEquals("Sean", resp.getFirstName());
        assertEquals("Huni", resp.getLastName());
        assertEquals("1501246344184", resp.getIdNumber());
        assertNotNull(resp.getTeam());
    }
}
