package com.xib.assessment.service;

import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.persistence.model.Team;
import com.xib.assessment.persistence.repo.TeamRepo;
import com.xib.assessment.util.TeamStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TeamServiceTest {
    @MockBean
    private TeamRepo teamRepo;

    @Autowired
    private TeamService teamService;

    @Test
    @DisplayName("Find All teams - Team Service")
    void givenTeamService_whenFindAllTeams_thenReturnAllTeams() {
        Collection<Team> teams = TeamStub.getTeamCollection();
        when(teamRepo.findAll()).thenReturn((List<Team>) teams);

        Collection<TeamDto> teamsDto = teamService.findAllTeams();

        assertNotNull(teamsDto);
        assertEquals(teams.size(), teamsDto.size());
        verify(teamRepo, times(1)).findAll();
    }

    @Test
    @DisplayName("Find team by ID - Team Service")
    void givenTeamService_whenFindTeamById_thenReturnSingleTeam() {
        Team team = TeamStub.getTeam();
        when(teamRepo.findById(3L)).thenReturn(java.util.Optional.of(team));

        TeamDto teamDto = teamService.findTeamById(3L);

        assertNotNull(teamDto);
        assertEquals(3, teamDto.getId());
        assertEquals("Mongo", teamDto.getName());
        verify(teamRepo, times(1)).findById(3L);
    }

    @Test
    @DisplayName("Save New Team - Team Service")
    void givenTeamService_whenSaveNewTeam_thenReturnSavedTeam() {
        Team team = TeamStub.getTeam();
        team.setId(null);
        Team expectedResp = new Team(3L, "Mongo");
        when(teamRepo.save(team)).thenReturn(expectedResp);

        TeamDto teamDto = teamService.saveTeam(new TeamDto(null, "Mongo"));

        assertNotNull(teamDto);
        assertEquals(3, teamDto.getId());
        assertEquals("Mongo", teamDto.getName());
        verify(teamRepo, times(1)).save(team);
    }
}