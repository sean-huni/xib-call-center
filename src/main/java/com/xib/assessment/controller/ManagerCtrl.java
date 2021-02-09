package com.xib.assessment.controller;


import com.xib.assessment.dto.ManagerDto;
import com.xib.assessment.service.ManagerService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@RestController
@RequestMapping("manager")
public class ManagerCtrl {
    private final ManagerService managerService;

    public ManagerCtrl(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<ManagerDto> findAllManagers() {
        return managerService.findAllManagers();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ManagerDto createNewManager(@RequestBody @NotNull @Valid ManagerDto manager) {
        return managerService.saveManager(manager);
    }
}
