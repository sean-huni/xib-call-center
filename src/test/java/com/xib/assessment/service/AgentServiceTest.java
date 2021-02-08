package com.xib.assessment.service;

import com.xib.assessment.dto.AgentDto;
import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.persistence.model.Agent;
import com.xib.assessment.persistence.model.Team;
import com.xib.assessment.persistence.repo.AgentRepo;
import com.xib.assessment.service.AgentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.xib.assessment.util.TestCases.getAgentsModel;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AgentServiceTest {
    @MockBean
    private AgentRepo agentRepo;

    @Autowired
    private AgentService agentService;

    @Test
    @DisplayName("Given AgentService, when mocking AgentRepo, find with pagination")
    void givenAgentService_whenMockingAgentRepo_andFindWithPagination_thenAgentDtoSuccess() {
        Sort sort = Sort.by(Sort.Direction.DESC, "firstName");
        Pageable pageable = PageRequest.of(0, 3, sort);

        Page<Agent> page = new PageImpl<>(new ArrayList<>(getAgentsModel()));

        when(agentRepo.findAll(pageable)).thenReturn(page);
        Collection<AgentDto> agents = agentService.findAllAgents(3, 0, Sort.Direction.DESC, "firstName");

        assertNotNull(agents);
        verify(agentRepo, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Given AgentService, when mocking AgentRepo, find All Agents")
    void givenAgentService_whenMockingAgentRepo_thenReturnAgentDtoCollection() {
        when(agentRepo.findAll()).thenReturn((List<Agent>) getAgentsModel());
        Collection<AgentDto> agents = agentService.findAllAgents();

        assertNotNull(agents);
        verify(agentRepo, times(1)).findAll();
    }

    @Test
    @DisplayName("Given AgentService, when mocking AgentRepo, find Agent By Id")
    void givenAgentService_whenMockingAgentRepo_andFindAgentById_thenReturnAgentDto() {
        when(agentRepo.findById(3L)).thenReturn(getAgentsModel().stream().filter(a-> a.getId() == 3).findFirst());
        AgentDto agent = agentService.findAgentById(3L);

        assertNotNull(agent);
        assertEquals(3L, agent.getId());
        verify(agentRepo, times(1)).findById(3L);
    }

    @Test
    @DisplayName("Given AgentService, when mocking AgentRepo, Save New Agent")
    void givenAgentService_whenMockingAgentRepo_andSaveNewAgent_thenReturnAgentDto() {
        AgentDto agent = new AgentDto(null, "Sean", "Huni", "1501246344184", new TeamDto(2L, "DC", null));
        Agent agentNoId = new Agent(null, "Sean", "Huni", "1501246344184", new Team(2L, "DC", null));
        Agent agentResp = new Agent(9L, "Sean", "Huni", "1501246344184", new Team(2L, "DC", null));

        when(agentRepo.save(agentNoId)).thenReturn(agentResp);
        AgentDto agentResp2 = agentService.saveAgent(agent);

        assertNotNull(agentResp2);
        assertEquals(9L, agentResp2.getId());
        verify(agentRepo, times(1)).save(agentNoId);
    }
}