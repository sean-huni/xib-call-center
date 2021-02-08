package com.xib.assessment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentDto {
    private Long id;
    @NotNull
    @Size(max = 255, min = 3)
    private String firstName;
    @NotNull
    @Size(max = 255, min = 3)
    private String lastName;
    @NotNull
    @Size(max = 13, min = 13)
    private String idNumber;

    @JsonProperty("team")
    private TeamDto teamDto;

    public AgentDto(Long id, @NotNull @Size(max = 255, min = 3) String firstName, @NotNull @Size(max = 255, min = 3) String lastName, @NotNull @Size(max = 13, min = 13) String idNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
    }
}
