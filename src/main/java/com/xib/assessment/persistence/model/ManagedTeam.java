package com.xib.assessment.persistence.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
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
