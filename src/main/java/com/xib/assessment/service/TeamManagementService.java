package com.xib.assessment.service;

import com.xib.assessment.dto.ManagerDto;
import com.xib.assessment.exception.ManagedTeamException;
import com.xib.assessment.exception.ManagerAlreadyAssignedException;
import com.xib.assessment.exception.ManagerNotFoundException;
import com.xib.assessment.exception.TeamNotFoundException;

public interface TeamManagementService {

    ManagerDto assignManager(Long teamId, Long managerId) throws TeamNotFoundException, ManagerNotFoundException, ManagerAlreadyAssignedException, ManagedTeamException;
}
