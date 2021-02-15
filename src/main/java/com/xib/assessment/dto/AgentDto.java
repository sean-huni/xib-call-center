package com.xib.assessment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class AgentDto {
    private Long id;
    @NotNull(message = "validation.constraint.notNull.agent.firstName")
    @NotEmpty(message = "validation.constraint.empty.agent.firstName")
    @Size(max = 255, min = 3, message = "validation.constraint.size.agent.firstName")
    private String firstName;
    @NotNull(message = "validation.constraint.notNull.agent.lastName")
    @NotEmpty(message = "validation.constraint.empty.agent.lastName")
    @Size(max = 255, min = 3, message = "validation.constraint.size.agent.lastName")
    private String lastName;
    @NotNull(message = "validation.constraint.notNull.agent.idNumber")
    @NotEmpty(message = "validation.constraint.empty.agent.idNumber")
    @Size(max = 13, min = 13, message = "validation.constraint.size.agent.idNumber")
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
