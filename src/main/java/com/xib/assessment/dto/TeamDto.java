package com.xib.assessment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {
    @Min(value = 1)
    private Long id;
    @NotNull
    @Size(max = 255, min = 2)
    private String name;

    @JsonProperty("agents")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Collection<AgentDto> agentDtoCollection;

    public TeamDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
