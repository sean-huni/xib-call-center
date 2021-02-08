package com.xib.assessment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {
    private Long id;
    private String name;

    @JsonProperty("agents")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Collection<AgentDto> agentDtoCollection;

    public TeamDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
