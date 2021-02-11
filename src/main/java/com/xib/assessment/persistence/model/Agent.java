package com.xib.assessment.persistence.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
public class Agent extends AbstractModelClass {
    private String firstName;
    private String lastName;
    private String idNumber;
    @OneToOne
    private Manager reportsTo;
    @ManyToOne
    private Team team;

    public Agent(Long id, String firstName, String lastName, String idNumber) {
        super.setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
    }

    public Agent(Long id, String firstName, String lastName, String idNumber, Team team) {
        super.setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.team = team;
    }
}
