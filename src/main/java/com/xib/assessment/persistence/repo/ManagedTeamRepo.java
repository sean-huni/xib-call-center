package com.xib.assessment.persistence.repo;

import com.xib.assessment.persistence.model.ManagedTeam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagedTeamRepo extends JpaRepository<ManagedTeam, Long> {

    ManagedTeam findManagedTeamByTeam_IdAndAndManager_Id(Long teamId, Long managerId);

    Integer countManagedTeamsByTeam_Id(Long id);
}
