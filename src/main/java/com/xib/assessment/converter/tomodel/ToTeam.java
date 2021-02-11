package com.xib.assessment.converter.tomodel;

import com.xib.assessment.dto.AgentDto;
import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.persistence.model.Agent;
import com.xib.assessment.persistence.model.Team;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ToTeam implements Converter<TeamDto, Team> {
    private final Converter<AgentDto, Agent> agentConverter;

    public ToTeam(Converter<AgentDto, Agent> agentConverter) {
        this.agentConverter = agentConverter;
    }

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public Team convert(TeamDto source) {
        return Objects.nonNull(source) ? new Team(source.getId(), source.getName(), toAgentCollection(source.getAgentDtoCollection())) : null;
    }

    private Collection<Agent> toAgentCollection(Collection<AgentDto> agentDtoCollection) {
        return Objects.nonNull(agentDtoCollection) ? agentDtoCollection.stream().map(agentConverter::convert).collect(Collectors.toList()) : null;
    }
}
