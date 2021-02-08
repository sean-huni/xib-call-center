package com.xib.assessment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    public TeamDto(@NotNull(message = "validation.constraint.notNull.team.name") @NotEmpty(message = "validation.constraint.empty.team.name") @Size(max = 255, min = 2, message = "validation.constraint.size.team.name") String name) {
        this.name = name;
    }

    public TeamDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
