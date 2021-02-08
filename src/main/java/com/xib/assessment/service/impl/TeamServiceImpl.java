package com.xib.assessment.service.impl;

import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.persistence.model.Team;
import com.xib.assessment.persistence.repo.TeamRepo;
import com.xib.assessment.service.TeamService;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Log4j2
@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepo teamRepo;
    private final Converter<Team, TeamDto> teamDtoConverter;
    private final Converter<TeamDto, Team> teamConverter;

    public TeamServiceImpl(TeamRepo teamRepo, Converter<Team, TeamDto> teamDtoConverter, Converter<TeamDto, Team> teamConverter) {
        this.teamRepo = teamRepo;
        this.teamDtoConverter = teamDtoConverter;
        this.teamConverter = teamConverter;
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
}
