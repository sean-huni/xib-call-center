package com.xib.assessment.util;

import com.xib.assessment.dto.AgentDto;
import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.persistence.model.Agent;
import com.xib.assessment.persistence.model.Team;

import java.util.ArrayList;
import java.util.Collection;

public class TestAgentStub {


    public static Collection<AgentDto> getAgents() {
        Collection<AgentDto> agents = new ArrayList<>();

        agents.add(new AgentDto(1L, "Sean", "Huni", "1501246344184", new TeamDto(1L, "DC", null)));
        agents.add(new AgentDto(2L, "Bruce", "Banner", "1011125190081", new TeamDto(1L, "DC", null)));
        agents.add(new AgentDto(3L, "Tony", "Stark", "6912115191083", new TeamDto(1L, "DC", null)));
        agents.add(new AgentDto(4L, "Peter", "Parker", "7801115190084", new TeamDto(1L, "DC", null)));
        agents.add(new AgentDto(5L, "Bruce", "Wayne", "6511185190085", new TeamDto(2L, "Marvel", null)));
        agents.add(new AgentDto(6L, "Clark", "Kent", "5905115190086", new TeamDto(2L, "Marvel", null)));

        return agents;
    }

    public static Collection<Agent> getAgentsModel() {
        Collection<Agent> agents = new ArrayList<>();

        agents.add(new Agent(1L, "Sean", "Huni", "1501246344184", new Team(1L, "DC", null)));
        agents.add(new Agent(2L, "Bruce", "Banner", "1011125190081", new Team(1L, "DC", null)));
        agents.add(new Agent(3L, "Tony", "Stark", "6912115191083", new Team(1L, "DC", null)));
        agents.add(new Agent(4L, "Peter", "Parker", "7801115190084", new Team(1L, "DC", null)));
        agents.add(new Agent(5L, "Bruce", "Wayne", "6511185190085", new Team(2L, "Marvel", null)));
        agents.add(new Agent(6L, "Clark", "Kent", "5905115190086", new Team(2L, "Marvel", null)));

        return agents;
    }
}
