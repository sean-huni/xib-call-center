package com.xib.assessment.controller;

import com.xib.assessment.dto.AgentDto;
import com.xib.assessment.service.AgentService;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("agent")
public class AgentCtrl {
    private final AgentService agentService;

    public AgentCtrl(AgentService agentService) {
        this.agentService = agentService;
    }

    @GetMapping
    public Collection<AgentDto> findAllAgents(@RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "page", required = false) Integer page,
                                              @RequestParam(value = "field", required = false) String fieldName, @RequestParam(value = "sort", required = false) Sort.Direction sort) {
        return size == null || page == null ? agentService.findAllAgents() : agentService.findAllAgents(size, page, sort, fieldName);
    }

    @GetMapping("{id}")
    public AgentDto findAgent(@PathVariable("id") Long id) {
        return agentService.findAgentById(id);
    }


}
