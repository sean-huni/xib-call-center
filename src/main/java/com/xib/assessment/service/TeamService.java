package com.xib.assessment.service;

import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.exception.AgentAlreadyAssignedException;
import com.xib.assessment.exception.AgentNotFoundException;
import com.xib.assessment.exception.AgentTeamAssignmentException;
import com.xib.assessment.exception.TeamNotFoundException;

import java.util.Collection;

public interface TeamService {

    Collection<TeamDto> findAllTeams();

    TeamDto findTeamById(Long id);

    TeamDto saveTeam(TeamDto team);

    TeamDto assignAgent(Long id, Long agentId) throws TeamNotFoundException, AgentNotFoundException, AgentAlreadyAssignedException, AgentTeamAssignmentException;

}
