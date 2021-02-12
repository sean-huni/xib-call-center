package com.xib.assessment.converter.todto;

import com.xib.assessment.dto.ManagedTeamDto;
import com.xib.assessment.dto.ManagerDto;
import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.persistence.model.ManagedTeam;
import com.xib.assessment.persistence.model.Manager;
import com.xib.assessment.persistence.model.Team;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ToManagedTeamDto implements Converter<ManagedTeam, ManagedTeamDto> {
    private final Converter<Team, TeamDto> teamDtoConverter;
    private final Converter<Manager, ManagerDto> managerDtoConverter;

    public ToManagedTeamDto(Converter<Team, TeamDto> teamDtoConverter, Converter<Manager, ManagerDto> managerDtoConverter) {
        this.teamDtoConverter = teamDtoConverter;
        this.managerDtoConverter = managerDtoConverter;
    }

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public ManagedTeamDto convert(ManagedTeam source) {
        return Objects.nonNull(source) ? new ManagedTeamDto(source.getId(), toTeamDto(source.getTeam()), toManagerDto(source.getManager())) : null;
    }

    private TeamDto toTeamDto(Team team) {
        return teamDtoConverter.convert(team);
    }

    private ManagerDto toManagerDto(Manager manager) {
        return managerDtoConverter.convert(manager);
    }
}
