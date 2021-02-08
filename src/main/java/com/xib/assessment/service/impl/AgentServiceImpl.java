package com.xib.assessment.service.impl;

import com.xib.assessment.converter.todto.ToAgentDto;
import com.xib.assessment.converter.tomodel.ToAgent;
import com.xib.assessment.dto.AgentDto;
import com.xib.assessment.persistence.model.Agent;
import com.xib.assessment.persistence.repo.AgentRepository;
import com.xib.assessment.service.AgentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
@Service
public class AgentServiceImpl implements AgentService {
    private final ToAgentDto toAgentDtoConverter;
    private final AgentRepository agentRepo;
    private ToAgent toAgentConverter;


    public AgentServiceImpl(ToAgentDto toAgentDtoConverter, AgentRepository agentRepo) {
        this.toAgentDtoConverter = toAgentDtoConverter;
        this.agentRepo = agentRepo;
    }

    /**
     * Finds existing agent by id.
     *
     * @param id of the Agent.
     * @return {@link AgentDto}
     */
    @Override
    public AgentDto findAgentById(Long id) {
        //Todo: Check for nulls

        //The Rest of the business logic
        return toAgentDtoConverter.convert(agentRepo.findById(id).orElse(null));
    }


    /**
     * Find all existing agents.
     * @return {@link  Collection <AgentDto>}
     */
    @Override
    public Collection<AgentDto> findAllAgents() {
        return agentRepo.findAll().stream().map(toAgentDtoConverter::convert).collect(Collectors.toList());
    }


    /**
     * Find all existing agents with {@link Pageable} & {@link Sort.Direction}
     *
     * @param size or number of elements per page.
     * @param page number of the results. Starts from page zero.
     * @param direction {@link org.springframework.data.domain.Sort.Direction}
     * @param fieldSort name of the field to sort.
     * @return {@link  Collection <AgentDto>}
     */
    @Override
    public Collection<AgentDto> findAllAgents(Integer size, Integer page, Sort.Direction direction, String fieldSort) {
        Field f = validateFieldWithFallback(fieldSort);

        direction = Objects.isNull(direction) ? Sort.DEFAULT_DIRECTION : direction;

        Sort sort = Sort.by(direction, f.getName());
        Pageable pageable = PageRequest.of(page, size, sort);
        return agentRepo.findAll(pageable).stream().map(toAgentDtoConverter::convert).collect(Collectors.toList());
    }

    private Field validateFieldWithFallback(String fieldSort) {
        Field f;
        try {
            f = Agent.class.getDeclaredField(fieldSort);
        } catch (NoSuchFieldException e) {
            log.error("field not found.", e);
            f = Arrays.stream(Agent.class.getDeclaredFields()).findFirst().get();
            log.info("Fallback Sort-by: " + f.getName());
        }
        return f;
    }

    /**
     * Finds existing agent by id.
     *
     * @param agent {@link AgentDto} to be saved.
     * @return saved {@link AgentDto}.
     */
    @Override
    public AgentDto saveAgent(AgentDto agent) {
        Agent agentSaved = agentRepo.save(toAgentConverter.convert(agent));

        return toAgentDtoConverter.convert(agentSaved);
    }


    @Autowired
    public void setToAgentConverter(ToAgent toAgentConverter) {
        this.toAgentConverter = toAgentConverter;
    }
}
