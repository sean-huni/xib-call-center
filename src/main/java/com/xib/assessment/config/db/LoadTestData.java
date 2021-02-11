package com.xib.assessment.config.db;

import com.xib.assessment.persistence.model.Agent;
import com.xib.assessment.persistence.model.ManagedTeam;
import com.xib.assessment.persistence.model.Manager;
import com.xib.assessment.persistence.model.Team;
import com.xib.assessment.persistence.repo.AgentRepo;
import com.xib.assessment.persistence.repo.ManagedTeamRepo;
import com.xib.assessment.persistence.repo.ManagerRepo;
import com.xib.assessment.persistence.repo.TeamRepo;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Configuration
public class LoadTestData {
    private final AgentRepo agentRepo;
    private final ManagerRepo managerRepo;
    private final ManagedTeamRepo managedTeamRepo;
    private final TeamRepo teamRepo;

    public LoadTestData(AgentRepo agentRepo, ManagerRepo managerRepo, ManagedTeamRepo managedTeamRepo, TeamRepo teamRepo) {
        this.agentRepo = agentRepo;
        this.managerRepo = managerRepo;
        this.managedTeamRepo = managedTeamRepo;
        this.teamRepo = teamRepo;
    }

    @PostConstruct
    @Transactional
    public void execute() {
        Team team1 = createTeam("Marvel");
        Team team2 = createTeam("DC");
        Team team3 = createTeam("Unknown");
        Team team4 = createTeam("Unassigned");

        Manager manager1 = createManager("Top", "Doug", "fake@email.com");
        Manager manager2 = createManager("Dez", "Bizz", "test@email.com");
        saveManagedTeam(manager1, team1);
        saveManagedTeam(manager1, team2);
        saveManagedTeam(manager2, team1);

        createAgent("Bruce", "Banner", "1011125190081", manager1, team1);
        createAgent("Tony", "Stark", "6912115191083", manager1, team1);
        createAgent("Peter", "Parker", "7801115190084", manager1, team1);
        createAgent("Bruce", "Wayne", "6511185190085", manager2, team2);
        createAgent("Clark", "Kent", "5905115190086", manager2, team2);
        createAgent("Rimo", "Bingo", "5905115190012", manager1, null);

    }

    private Team createTeam(String name) {
        Team t = new Team();
        t.setName(name);
        return teamRepo.save(t);
    }

    private Manager createManager(String firstName, String lastName, String email){
        Manager m = new Manager(null, firstName, lastName, email);
        return managerRepo.save(m);
    }

    private void saveManagedTeam(Manager manager, Team team){
        ManagedTeam m = new ManagedTeam();
        m.setManager(manager);
        m.setTeam(team);
        managedTeamRepo.save(m);
    }

    private Agent createAgent(String firstName, String lastName, String idNumber, Manager reportsTop, Team team) {
        Agent a = new Agent();
        a.setFirstName(firstName);
        a.setLastName(lastName);
        a.setIdNumber(idNumber);
        a.setReportsTo(reportsTop);
        a.setTeam(team);
        return agentRepo.save(a);
    }
}
