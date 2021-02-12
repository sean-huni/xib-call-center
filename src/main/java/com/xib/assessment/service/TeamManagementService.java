package com.xib.assessment.service;

import com.xib.assessment.dto.ManagedTeamDto;
import com.xib.assessment.exception.ManagedTeamException;
import com.xib.assessment.exception.ManagerAlreadyAssignedException;
import com.xib.assessment.exception.ManagerNotFoundException;
import com.xib.assessment.exception.TeamNotFoundException;

public interface TeamManagementService {

    ManagedTeamDto assignManager(Long teamId, Long managerId) throws TeamNotFoundException, ManagerNotFoundException, ManagerAlreadyAssignedException, ManagedTeamException;

    ManagedTeamDto findManagedTeamByTeamIdAndManagerId(Long teamId, Long managerId);
}
