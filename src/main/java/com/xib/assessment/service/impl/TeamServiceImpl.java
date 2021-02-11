package com.xib.assessment.service.impl;

import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.exception.AgentAlreadyAssignedException;
import com.xib.assessment.exception.AgentNotFoundException;
import com.xib.assessment.exception.AgentTeamAssignmentException;
import com.xib.assessment.exception.TeamNotFoundException;
import com.xib.assessment.persistence.model.Agent;
import com.xib.assessment.persistence.model.ManagedTeam;
import com.xib.assessment.persistence.model.Team;
import com.xib.assessment.persistence.repo.AgentRepo;
import com.xib.assessment.persistence.repo.TeamRepo;
import com.xib.assessment.service.TeamService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepo teamRepo;
    private final Converter<Team, TeamDto> teamDtoConverter;
    private final Converter<TeamDto, Team> teamConverter;
    private AgentRepo agentRepo;

    public TeamServiceImpl(TeamRepo teamRepo, Converter<Team, TeamDto> teamDtoConverter, Converter<TeamDto, Team> teamConverter) {
        this.teamRepo = teamRepo;
        this.teamDtoConverter = teamDtoConverter;
        this.teamConverter = teamConverter;
    }

    @Autowired
    public void setAgentRepo(AgentRepo agentRepo) {
        this.agentRepo = agentRepo;
    }

    @Override
    public Collection<TeamDto> findAllTeams() {
       List<Team> teams = teamRepo.findAll();
        Collection<TeamDto> teamDtoCollection = teams.stream().map(teamDtoConverter::convert).collect(Collectors.toList());
        return teamDtoCollection;
    }

    @Override
    public TeamDto findTeamById(Long id) {
        Team team = teamRepo.findById(id).orElse(null);
        return teamDtoConverter.convert(team);
    }

    @Override
    public TeamDto saveTeam(TeamDto team) {
        Team t = teamConverter.convert(team);
        return teamDtoConverter.convert(teamRepo.save(t));
    }

    @Override
    public TeamDto assignAgent(Long id, Long agentId) throws TeamNotFoundException, AgentNotFoundException, AgentAlreadyAssignedException, AgentTeamAssignmentException {
        Optional<Team> t = teamRepo.findById(id);
        if (!t.isPresent()) {
            throw new TeamNotFoundException("validation.error.notFound.team", id);
        }

        Optional<Agent> a = agentRepo.findById(agentId);
        if (!a.isPresent()) {
            throw new AgentNotFoundException("validation.error.notFound.agent", agentId);
        }

        //Check is the agent is already assigned to another team.
        if (Objects.nonNull(a.get().getTeam()) && Objects.nonNull(a.get().getTeam().getId())) {
            throw new AgentAlreadyAssignedException("validation.error.assigned.agent.team", agentId, a.get().getTeam().getId());
        }

        //Check if the Agent reports to any Manager
        //Check If the Manager that the agent reports to is also managing the team that agent wishes to join.
        Set<ManagedTeam> teams = !t.get().getManagedTeams().isEmpty() && Objects.nonNull(a.get().getReportsTo()) ? t.get().getManagedTeams().stream().filter(mt -> Objects.nonNull(mt.getManager()) && a.get().getReportsTo().getId().equals(mt.getManager().getId())).collect(Collectors.toSet()) : null;
        if (teams == null || teams.isEmpty()) {
            throw new AgentTeamAssignmentException("validation.error.assigned.agent.manager", agentId);
        }

        //An agent cannot be reassigned to a team that they're already assigned to
        if (!t.get().getAgents().contains(a.get())) {
            t.get().getAgents().add(a.get());
        } else {
            throw new AgentAlreadyAssignedException("validation.error.assigned.agent.team", agentId, id);
        }

        return teamDtoConverter.convert(teamRepo.save(t.get()));
    }
}
