package com.xib.assessment.converter.todto;

import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.persistence.model.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.converter.Converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ToTeamDtoTest {
    @Autowired
    private Converter<Team, TeamDto> toTeamDto;

    @Test
    @DisplayName("")
    void givenTeamConverter_whenConvertingFromTeamToTeamDto_thenReturnTeamDto() {
        Team agentResp = new Team(1L, "Jumbo");

        TeamDto resp = toTeamDto.convert(agentResp);

        assertEquals(1, resp.getId());
        assertEquals("Jumbo", resp.getName());
        assertTrue(resp.getAgentDtoCollection().isEmpty());
    }


    @Test
    void givenTeamConverter_whenConvertingFromTeamDtoToTeam_thenReturnNull() {
        TeamDto resp = toTeamDto.convert(null);

        assertNull(resp);
    }


}