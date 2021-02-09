package com.xib.assessment.persistence.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@NoArgsConstructor
public class Manager extends AbstractModelClass {
    private String firstName;
    private String lastName;
    private String email;

    @ManyToMany
    @JoinTable(name = "managed_teams", joinColumns = @JoinColumn(name = "manager_id"), inverseJoinColumns = @JoinColumn(name = "team_id"))
    @Setter(value = AccessLevel.NONE)
    private Collection<Team> managedTeams = new ArrayList<>();


    public Manager(Long id, String firstName, String lastName, String email) {
        super.setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public void setManagedTeams(Collection<Team> teams) {
        this.managedTeams.addAll(teams);
    }
}
