package com.xib.assessment.service;

import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.exception.AgentAlreadyAssignedException;
import com.xib.assessment.exception.AgentNotFoundException;
import com.xib.assessment.exception.TeamNotFoundException;
import com.xib.assessment.persistence.model.Agent;
import com.xib.assessment.persistence.model.Team;
import com.xib.assessment.persistence.repo.AgentRepo;
import com.xib.assessment.persistence.repo.TeamRepo;
import com.xib.assessment.util.TeamStub;
import com.xib.assessment.util.TestAgentStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TeamServiceTest {
    @MockBean
    private TeamRepo teamRepo;

    @MockBean
    private AgentRepo agentRepo;

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

    @Test
    @DisplayName("Assign existing agent to the team - Throw AgentAlreadyAssignedException")
    void givenAgentIdAndTeamId_whenAssigningAgentToTeam_thenThrowAgentAlreadyAssignedException() {
        Optional<Agent> agent = TestAgentStub.getAgentsModel().stream().findAny();
        Optional<Team> expectedResp = Optional.of(TeamStub.getTeam());
        expectedResp.get().getAgents().add(agent.get());

        when(agentRepo.findById(1L)).thenReturn(agent);
        when(teamRepo.findById(1L)).thenReturn(expectedResp);
        when(teamRepo.save(any(Team.class))).thenReturn(expectedResp.get());

        AgentAlreadyAssignedException exception = assertThrows(AgentAlreadyAssignedException.class, () -> teamService.assignAgent(1L, 1L));
        assertEquals("validation.error.assigned.team", exception.getMessage());
        assertEquals(1, exception.getAgentId());
        assertEquals(1, exception.getTeamId());
        verify(teamRepo, times(0)).save(any(Team.class));
    }

    @Test
    @DisplayName("Assign existing agent to the team - Throw TeamNotFoundException")
    void givenAgentIdAndTeamId_whenAssigningAgentToTeam_thenThrowTeamNotFoundException() {
        Optional<Agent> agent = TestAgentStub.getAgentsModel().stream().findAny();
        Optional<Team> expectedResp = Optional.of(TeamStub.getTeam());
        expectedResp.get().getAgents().add(agent.get());

        when(agentRepo.findById(12L)).thenReturn(agent);
        when(teamRepo.findById(10L)).thenReturn(expectedResp);
        when(teamRepo.save(any(Team.class))).thenReturn(expectedResp.get());

        TeamNotFoundException exception = assertThrows(TeamNotFoundException.class, () -> teamService.assignAgent(12L, 10L));
        assertEquals("validation.error.notFound.team", exception.getMessage());
        assertEquals(12, exception.getId());
        verify(teamRepo, times(0)).save(any(Team.class));
    }


    @Test
    @DisplayName("Assign existing agent to the team - ")
    void givenAgentIdAndTeamId_whenAssigningAgentToTeam_thenReturnAssignedMemberToTeam() throws TeamNotFoundException, AgentNotFoundException, AgentAlreadyAssignedException {
        Optional<Agent> agent = TestAgentStub.getAgentsModel().stream().filter(a-> a.getId()==5L).findFirst();
        agent.get().setTeam(null);
        Optional<Team> expectedResp = Optional.of(TeamStub.getTeam());

        when(agentRepo.findById(5L)).thenReturn(agent);
        when(teamRepo.findById(1L)).thenReturn(expectedResp);

        when(teamRepo.save(any(Team.class))).thenReturn(expectedResp.get());

        TeamDto teamDto = teamService.assignAgent(1L, 5L);

        assertNotNull(teamDto);
        assertEquals(3, teamDto.getId());
        assertEquals("Mongo", teamDto.getName());
        verify(teamRepo, times(1)).save(any(Team.class));
    }

}