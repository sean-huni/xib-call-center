package com.xib.assessment.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Agent extends AbstractModelClass {

    private String firstName;
    private String lastName;
    private String idNumber;
    @ManyToOne
    private Team team;

    public Agent(Long id, String firstName, String lastName, String idNumber) {
        setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
    }

    public Agent(Long id, String firstName, String lastName, String idNumber, Team team) {
        setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.team = team;
    }

}
