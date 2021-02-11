package com.xib.assessment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xib.assessment.persistence.model.ManagedTeam;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {
    @Min(value = 1, message = "validation.constraint.min.team.id")
    private Long id;
    @NotNull(message = "validation.constraint.notNull.team.name")
    @NotEmpty(message = "validation.constraint.empty.team.name")
    @Size(max = 255, min = 2, message = "validation.constraint.size.team.name")
    private String name;

    @JsonProperty("agents")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Collection<AgentDto> agentDtoCollection;

    @Setter(value = AccessLevel.NONE)
    private Collection<ManagedTeam> managedTeams = new ArrayList<>();

    public TeamDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
