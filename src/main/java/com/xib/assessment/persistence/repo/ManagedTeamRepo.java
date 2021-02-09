package com.xib.assessment.persistence.repo;

import com.xib.assessment.persistence.model.ManagedTeam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagedTeamRepo extends JpaRepository<ManagedTeam, Long> {
}
