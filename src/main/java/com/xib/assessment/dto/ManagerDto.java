package com.xib.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDto {

    private Long id;
    @NotNull(message = "validation.constraint.notNull.manager.firstName")
    @NotEmpty(message = "validation.constraint.empty.manager.firstName")
    @Size(max = 255, min = 3, message = "validation.constraint.size.manager.firstName")
    private String firstName;
    @NotNull(message = "validation.constraint.notNull.manager.lastName")
    @NotEmpty(message = "validation.constraint.empty.manager.lastName")
    @Size(max = 255, min = 3, message = "validation.constraint.size.manager.lastName")
    private String lastName;
    @NotNull(message = "validation.constraint.notNull.manager.email")
    @NotEmpty(message = "validation.constraint.empty.manager.email")
    @Email(message = "validation.constraint.format.manager.email")
    @Size(max = 85, min = 4, message = "validation.constraint.size.manager.email")
    private String email;
}
