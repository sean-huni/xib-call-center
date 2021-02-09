package com.xib.assessment.converter.todto;

import com.xib.assessment.dto.AgentDto;
import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.persistence.model.Agent;
import com.xib.assessment.persistence.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ToTeamDto implements Converter<Team, TeamDto> {
    private Converter<Agent, AgentDto> toAgentDtoConverter;

    @Autowired
    public void setToAgentDtoConverter(Converter<Agent, AgentDto> toAgentDtoConverter) {
        this.toAgentDtoConverter = toAgentDtoConverter;
    }

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public TeamDto convert(Team source) {
        return Objects.nonNull(source) ? new TeamDto(source.getId(), source.getName(), toAgentDtoCollection(source.getAgents()), source.getManagedTeams()) : null;
    }

    private Collection<AgentDto> toAgentDtoCollection(Collection<Agent> agents) {
        return Objects.nonNull(agents) ? agents.stream().map(a -> toAgentDtoConverter.convert(a)).collect(Collectors.toList()) : null;
    }
}
