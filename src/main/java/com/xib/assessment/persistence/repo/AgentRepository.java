package com.xib.assessment.persistence.repo;

import com.xib.assessment.persistence.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent, Long> {

}
