package com.xib.assessment.persistence;

import com.xib.assessment.persistence.model.Agent;
import com.xib.assessment.persistence.model.Team;
import com.xib.assessment.persistence.repo.AgentRepository;
import com.xib.assessment.persistence.repo.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Component
public class LoadTestData {
    private final AgentRepository agentRepository;

    private final TeamRepository teamRepository;

    public LoadTestData(AgentRepository agentRepository, TeamRepository teamRepository) {
        this.agentRepository = agentRepository;
        this.teamRepository = teamRepository;
    }

    @PostConstruct
    @Transactional
    public void execute() {
        Team team1 = createTeam("Marvel");
        Team team2 = createTeam("DC");

        createAgent("Bruce", "Banner", "1011125190081", team1);
        createAgent("Tony", "Stark", "6912115191083", team1);
        createAgent("Peter", "Parker", "7801115190084", team1);
        createAgent("Bruce", "Wayne", "6511185190085", team2);
        createAgent("Clark", "Kent", "5905115190086",team2);
    }

    private Team createTeam(String name) {
        Team t = new Team();
        t.setName(name);
        return teamRepository.save(t);
    }

    private Agent createAgent(String firstName, String lastName, String idNumber, Team team) {
        Agent a = new Agent();
        a.setFirstName(firstName);
        a.setLastName(lastName);
        a.setIdNumber(idNumber);
        a.setTeam(team);
        return agentRepository.save(a);
    }
}
