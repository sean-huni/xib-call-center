package com.xib.assessment.converter.todto;

import com.xib.assessment.dto.AgentDto;
import com.xib.assessment.persistence.model.Agent;
import com.xib.assessment.persistence.model.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.converter.Converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class ToAgentDtoTest {
    @Autowired
    private Converter<Agent, AgentDto> toAgentDto;

    @Test
    void givenAgentConverter_whenConvertingFromAgentToAgentDto() {
        Agent agentResp = new Agent(123L, "Sean", "Huni", "1501246344184", new Team(1L, "DC", null));

        AgentDto resp = toAgentDto.convert(agentResp);

        assertEquals(123L, resp.getId());
        assertEquals("Sean", resp.getFirstName());
        assertEquals("Huni", resp.getLastName());
        assertEquals("1501246344184", resp.getIdNumber());
        assertNotNull(resp.getTeamDto());
    }

    @Test
    void givenAgentConverter_whenConvertingFromAgentConstructorToAgentDto() {
        Agent agentResp = new Agent(1223L, "Sean", "Huni", "1501246344184");

        AgentDto resp = toAgentDto.convert(agentResp);

        assertEquals(1223L, resp.getId());
        assertEquals("Sean", resp.getFirstName());
        assertEquals("Huni", resp.getLastName());
        assertEquals("1501246344184", resp.getIdNumber());
        assertNull(resp.getTeamDto());
    }

    @Test
    void givenAgentConverter_whenConvertingFromAgentToNull() {

        AgentDto resp = toAgentDto.convert(null);

        assertNull(resp);
    }
}