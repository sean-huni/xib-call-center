package com.xib.assessment.converter.tomodel;

import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.persistence.model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.converter.Converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ToTeamModelTest {
    @Autowired
    private Converter<TeamDto, Team> toTeamDto;

    @BeforeEach
    void setup() {
    }

    @Test
    @DisplayName("TeamDto Converter - ToTeamModel Component")
    void givenTeamConverter_whenConvertingFromTeamToTeamDto() {
        TeamDto teamResp = new TeamDto(1L, "Jumbo-2");

        Team resp = toTeamDto.convert(teamResp);

        assertEquals(1, resp.getId());
        assertEquals("Jumbo-2", resp.getName());
        assertTrue(resp.getAgents().isEmpty());
    }


    @Test
    @DisplayName("Team Converter Null Values - ToTeamModel Component")
    void givenTeamConverter_withNullParam_whenConvertingFromTeamToTeamDto_thenReturnNull() {
        Team resp = toTeamDto.convert(null);

        assertNull(resp);
    }
}
