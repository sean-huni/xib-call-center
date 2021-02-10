package com.xib.assessment.controller;

import com.xib.assessment.dto.ManagedTeamDto;
import com.xib.assessment.dto.ManagerDto;
import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.exception.AgentAlreadyAssignedException;
import com.xib.assessment.exception.AgentNotFoundException;
import com.xib.assessment.exception.ManagedTeamException;
import com.xib.assessment.exception.ManagerAlreadyAssignedException;
import com.xib.assessment.exception.ManagerNotFoundException;
import com.xib.assessment.exception.TeamNotFoundException;
import com.xib.assessment.persistence.model.Manager;
import com.xib.assessment.service.TeamManagementService;
import com.xib.assessment.service.TeamService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@RestController
@RequestMapping("team")
public class TeamCtrl {
    private final TeamService teamService;
    private TeamManagementService teamManagementService;

    public TeamCtrl(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<TeamDto> findAllTeams() {
        return teamService.findAllTeams();
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TeamDto findTeamById(@PathVariable("id") Long id) {
        return teamService.findTeamById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TeamDto createNewTeam(@RequestBody @Valid @NotNull TeamDto team) {
        return teamService.saveTeam(team);
    }

    @PutMapping(value = "/{id}/agent/{agent-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TeamDto assignAgentToTeam(@PathVariable("id") Long id, @PathVariable("agent-id") Long agentId) throws TeamNotFoundException, AgentNotFoundException, AgentAlreadyAssignedException {
      return teamService.assignAgent(id, agentId);
    }

    @PutMapping(value = "/{id}/manger/{manager-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ManagerDto assignMangerToTeam(@PathVariable("id") Long id, @PathVariable("manager-id") Long managerId) throws TeamNotFoundException, ManagerNotFoundException, ManagerAlreadyAssignedException, ManagedTeamException {
      return teamManagementService.assignManager(id, managerId);
    }
}
