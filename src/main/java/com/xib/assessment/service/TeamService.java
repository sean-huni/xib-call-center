package com.xib.assessment.service;

import com.xib.assessment.dto.TeamDto;

import java.util.Collection;

public interface TeamService {

    Collection<TeamDto> findAllTeams();

    TeamDto findTeamById(Long id);

    TeamDto saveTeam(TeamDto team);

}
