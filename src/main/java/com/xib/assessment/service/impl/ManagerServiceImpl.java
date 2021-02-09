package com.xib.assessment.service.impl;

import com.xib.assessment.dto.ManagerDto;
import com.xib.assessment.persistence.model.Manager;
import com.xib.assessment.persistence.repo.ManagerRepo;
import com.xib.assessment.service.ManagerService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ManagerServiceImpl implements ManagerService {
    private final ManagerRepo managerRepo;
    private final Converter<Manager, ManagerDto> managerDtoConverter;
    private final Converter<ManagerDto, Manager> managerConverter;

    public ManagerServiceImpl(ManagerRepo managerRepo, Converter<Manager, ManagerDto> managerDtoConverter, Converter<ManagerDto, Manager> managerConverter) {
        this.managerRepo = managerRepo;
        this.managerDtoConverter = managerDtoConverter;
        this.managerConverter = managerConverter;
    }

    @Override
    public ManagerDto saveManager(ManagerDto manager) {
        Manager managerUnsaved = managerConverter.convert(manager);

        return Objects.nonNull(managerUnsaved) ? managerDtoConverter.convert(managerRepo.save(managerUnsaved)) : null;
    }

    @Override
    public Collection<ManagerDto> findAllManagers() {
        return managerRepo.findAll().stream().map(m -> managerDtoConverter.convert(m)).collect(Collectors.toList());
    }
}
