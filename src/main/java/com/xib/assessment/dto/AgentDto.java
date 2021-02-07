package com.xib.assessment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String idNumber;

    @JsonProperty("team")
    private TeamDto teamDto;
}
