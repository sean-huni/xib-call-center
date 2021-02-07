package com.xib.assessment.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany
    private Collection<Agent> agents;
}
