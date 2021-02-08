package com.xib.assessment.service.impl;

import com.xib.assessment.dto.AgentDto;
import com.xib.assessment.persistence.model.Agent;
import com.xib.assessment.persistence.repo.AgentRepo;
import com.xib.assessment.service.AgentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Collection;

import static com.xib.assessment.util.TestCases.getAgentsModel;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AgentServiceImplTest {
    @Mock
    private AgentRepo agentRepo;

    @Autowired
    private AgentService agentService;

    @Test
    void given() {
        Sort sort = Sort.by(Sort.Direction.DESC,"firstName");
        Pageable pageable = PageRequest.of(0, 3, sort);

        Page<Agent> page = new PageImpl<>(new ArrayList<>(getAgentsModel()));

        when(agentRepo.findAll(pageable)).thenReturn(page);
        Collection<AgentDto> agents = agentService.findAllAgents(3, 0, Sort.Direction.DESC, "firstName");

        assertNotNull(agents);
        verify(agentRepo, times(0)).findAll(pageable);
    }
}