package com.xib.assessment.dto;

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
    private Collection<AgentDto> agentDtoCollection;
}
