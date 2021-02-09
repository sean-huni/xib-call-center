package com.xib.assessment.util;

import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.persistence.model.Team;

import java.util.ArrayList;
import java.util.Collection;

public class TeamStub {

    public static Team getTeam() {
        return new Team(3L, "Mongo");
    }

    public static TeamDto getTeamDto() {
        return new TeamDto(3L, "Mongo");
    }

    public static Collection<TeamDto> getTeamDtoCollection() {
        Collection<TeamDto> teamDto = new ArrayList<>();
        teamDto.add(new TeamDto(3L, "Mongo"));
        teamDto.add(new TeamDto(4L, "Zeus"));
        teamDto.add(new TeamDto(5L, "Jumbo"));
        return teamDto;
    }

    public static Collection<Team> getTeamCollection() {
        Collection<Team> team = new ArrayList<>();
        team.add(new Team(3L, "Mongo"));
        team.add(new Team(4L, "Zeus"));
        team.add(new Team(5L, "Jumbo"));
        return team;
    }


}
