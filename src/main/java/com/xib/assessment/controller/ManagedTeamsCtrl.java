package com.xib.assessment.controller;

import com.xib.assessment.dto.ManagedTeamDto;
import com.xib.assessment.service.TeamManagementService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("managed-team")
public class ManagedTeamsCtrl {
    private final TeamManagementService teamManagementService;

    public ManagedTeamsCtrl(TeamManagementService teamManagementService) {
        this.teamManagementService = teamManagementService;
    }

    @GetMapping("find")
    public ManagedTeamDto findManagedTeamByTeamIdAndManagerId(@NotNull @RequestParam("team-id") Long teamId, @NotNull @RequestParam("manager-id") Long managerId) {
        return teamManagementService.findManagedTeamByTeamIdAndManagerId(teamId, managerId);
    }
}
