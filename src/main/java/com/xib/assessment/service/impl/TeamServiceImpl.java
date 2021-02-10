package com.xib.assessment.service.impl;

import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.exception.AgentAlreadyAssignedException;
import com.xib.assessment.exception.AgentNotFoundException;
import com.xib.assessment.exception.TeamNotFoundException;
import com.xib.assessment.persistence.model.Agent;
import com.xib.assessment.persistence.model.Team;
import com.xib.assessment.persistence.repo.AgentRepo;
import com.xib.assessment.persistence.repo.TeamRepo;
import com.xib.assessment.service.TeamService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
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
        return teamRepo.findAll().stream().map(t -> teamDtoConverter.convert(t)).collect(Collectors.toList());
    }

    @Override
    public TeamDto findTeamById(Long id) {
        return teamDtoConverter.convert(teamRepo.findById(id).orElse(null));
    }

    @Override
    public TeamDto saveTeam(TeamDto team) {
        Team t = teamConverter.convert(team);
        return teamDtoConverter.convert(teamRepo.save(t));
    }

    @Override
    public TeamDto assignAgent(Long id, Long agentId) throws TeamNotFoundException, AgentNotFoundException, AgentAlreadyAssignedException {
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

        //An agent cannot be reassigned to a team that they're already assigned to
        if (!t.get().getAgents().contains(a.get())) {
            t.get().getAgents().add(a.get());
        } else {
            throw new AgentAlreadyAssignedException("validation.error.assigned.agent.team", agentId, id);
        }

        return teamDtoConverter.convert(teamRepo.save(t.get()));
    }
}
