package com.xib.assessment.service;

import com.xib.assessment.dto.ManagerDto;

import java.util.Collection;

public interface ManagerService {

    ManagerDto saveManager(ManagerDto manager);

    Collection<ManagerDto> findAllManagers();
}
