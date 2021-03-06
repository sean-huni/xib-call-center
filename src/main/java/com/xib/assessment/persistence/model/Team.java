package com.xib.assessment.persistence.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Team extends AbstractModelClass {
    private String name;

    @OneToMany(mappedBy = "team")
    @Setter(value = AccessLevel.NONE)
    private Collection<Agent> agents = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    @Setter(value = AccessLevel.NONE)
    private Collection<ManagedTeam> managedTeams = new ArrayList<>();

    public Team(Long id, String name) {
        setId(id);
        this.name = name;
    }

    public Team(Long id, String name, Collection<Agent> agents) {
        setId(id);
        this.name = name;
        this.agents = agents;
    }

    public Collection<Agent> getAgents() {
        agents = Objects.isNull(agents) ? new ArrayList<>() : agents;
        return agents;
    }
}
