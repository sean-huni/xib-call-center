package com.xib.assessment.persistence.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
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

    @OneToMany(mappedBy = "manager")
    @Setter(value = AccessLevel.NONE)
    private Collection<ManagedTeam> managedTeam = new ArrayList<>();


    public Manager(Long id, String firstName, String lastName, String email) {
        super.setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public void setManagedTeams(Collection<ManagedTeam> teams) {
        this.managedTeam.addAll(teams);
    }
}
