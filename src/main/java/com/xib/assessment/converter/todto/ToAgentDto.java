package com.xib.assessment.converter.todto;

import com.xib.assessment.dto.AgentDto;
import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.persistence.model.Agent;
import com.xib.assessment.persistence.model.Team;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ToAgentDto implements Converter<Agent, AgentDto> {

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public AgentDto convert(Agent source) {
        if (Objects.nonNull(source)) {
            Team oldTeam = source.getTeam();
            return new AgentDto(source.getId(), source.getFirstName(), source.getLastName(), source.getIdNumber(), new TeamDto(oldTeam.getId(), oldTeam.getName(), null));
        }
        return null;
    }
}
