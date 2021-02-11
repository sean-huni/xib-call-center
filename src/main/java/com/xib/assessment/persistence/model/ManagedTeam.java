package com.xib.assessment.persistence.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
public class ManagedTeam extends AbstractModelClass {

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public ManagedTeam() {
        super();
    }

}
