package com.xib.assessment.controller;

import com.xib.assessment.dto.AgentDto;
import com.xib.assessment.service.AgentService;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@RestController
@RequestMapping("agent")
public class AgentCtrl {
    private final AgentService agentService;

    public AgentCtrl(AgentService agentService) {
        this.agentService = agentService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<AgentDto> findAllAgents(@RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "page", required = false) Integer page,
                                              @RequestParam(value = "field", required = false) String fieldName, @RequestParam(value = "sort", required = false) Sort.Direction sort) {
        return size == null || page == null ? agentService.findAllAgents() : agentService.findAllAgents(size, page, sort, fieldName);
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AgentDto findAgent(@PathVariable("id") Long id) {
        return agentService.findAgentById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AgentDto saveAgent(@RequestBody @NotNull @Valid AgentDto agent) {
        return agentService.saveAgent(agent);
    }

}
