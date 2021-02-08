package com.xib.assessment.controller;

import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.service.TeamService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    public TeamCtrl(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
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
}
