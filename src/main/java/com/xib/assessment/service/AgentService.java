package com.xib.assessment.service;

import com.xib.assessment.dto.AgentDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collection;

public interface AgentService {

    /**
     * Finds existing agent by id.
     *
     * @param id of the Agent.
     * @return {@link AgentDto}
     */
    AgentDto findAgentById(Long id);

    /**
     * Find all existing agents with {@link Pageable} & {@link Sort.Direction}
     *
     * @param size or number of elements per page.
     * @param page number of the results. Starts from page zero.
     * @param direction {@link org.springframework.data.domain.Sort.Direction}
     * @param fieldSort name of the field to sort.
     * @return {@link  Collection <AgentDto>}
     */
    Collection<AgentDto> findAllAgents(Integer size, Integer page, Sort.Direction direction, String fieldSort);


    /**
     * Find all existing agents.
     * @return {@link  Collection <AgentDto>}
     */
    Collection<AgentDto> findAllAgents();

    /**
     * Finds existing agent by id.
     *
     * @param agent {@link AgentDto} to be saved.
     * @return saved {@link AgentDto}.
     */
    AgentDto saveAgent(AgentDto agent);


}
