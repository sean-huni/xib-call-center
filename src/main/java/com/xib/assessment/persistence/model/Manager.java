package com.xib.assessment.persistence.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@NoArgsConstructor
public class Manager extends AbstractModelClass {
    private String firstName;
    private String lastName;
    private String email;

    public Manager(Long id, String firstName, String lastName, String email) {
        super.setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
