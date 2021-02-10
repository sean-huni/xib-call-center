package com.xib.assessment.persistence.repo;

import com.xib.assessment.persistence.model.ManagedTeam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ManagedTeamRepo extends JpaRepository<ManagedTeam, Long> {

    Collection<ManagedTeam> findManagedTeamsByTeamEquals(Long id);
    Integer countManagedTeamsByTeam_Id(Long id);
}
