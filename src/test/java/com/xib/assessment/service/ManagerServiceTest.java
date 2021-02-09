package com.xib.assessment.service;

import com.xib.assessment.dto.ManagerDto;
import com.xib.assessment.persistence.model.Manager;
import com.xib.assessment.persistence.repo.ManagerRepo;
import com.xib.assessment.util.ManagerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.converter.Converter;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ManagerServiceTest {
    @Autowired
    Converter<ManagerDto, Manager> managerConverter;
    @MockBean
    private ManagerRepo managerRepo;
    @Autowired
    private ManagerService managerService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Save New Manager - Manager Service")
    void saveManager() throws Exception {
        Manager savedManager = ManagerStub.getManager();
        ManagerDto unsavedManager = ManagerStub.getManagersDto().stream().findFirst().orElseThrow(Exception::new);
        when(managerRepo.save(any(Manager.class))).thenReturn(savedManager);

        ManagerDto managerResp = managerService.saveManager(unsavedManager);

        assertNotNull(managerResp);
        verify(managerRepo, times(1)).save(managerConverter.convert(unsavedManager));
    }

    @Test
    @DisplayName("Find all Managers - Manager Service")
    void findAllManagers() {
        List<Manager> managers = (List<Manager>) ManagerStub.getManagers();
        when(managerRepo.findAll()).thenReturn(managers);

        Collection<ManagerDto> managersResp = managerService.findAllManagers();

        assertNotNull(managersResp);
        verify(managerRepo, times(1)).findAll();
    }
}