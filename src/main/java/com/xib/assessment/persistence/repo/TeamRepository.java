package com.xib.assessment.persistence.repo;

import com.xib.assessment.persistence.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
