package com.xib.assessment.service.impl;

import com.xib.assessment.dto.ManagerDto;
import com.xib.assessment.exception.ManagedTeamException;
import com.xib.assessment.exception.ManagerAlreadyAssignedException;
import com.xib.assessment.exception.ManagerNotFoundException;
import com.xib.assessment.exception.TeamNotFoundException;
import com.xib.assessment.persistence.model.ManagedTeam;
import com.xib.assessment.persistence.model.Manager;
import com.xib.assessment.persistence.model.Team;
import com.xib.assessment.persistence.repo.ManagedTeamRepo;
import com.xib.assessment.persistence.repo.ManagerRepo;
import com.xib.assessment.persistence.repo.TeamRepo;
import com.xib.assessment.service.TeamManagementService;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class TeamManagementServiceImpl implements TeamManagementService {
    private static final int MAX_ALLOWED_MANAGERS_PER_TEAM = 2;
    private final TeamRepo teamRepo;
    private final ManagerRepo managerRepo;
    private final ManagedTeamRepo managedTeamRepo;
    private final Converter<Manager, ManagerDto> managerDtoConverter;

    public TeamManagementServiceImpl(TeamRepo teamRepo, ManagerRepo managerRepo, ManagedTeamRepo managedTeamRepo, Converter<Manager, ManagerDto> managerDtoConverter) {
        this.teamRepo = teamRepo;
        this.managerRepo = managerRepo;
        this.managedTeamRepo = managedTeamRepo;
        this.managerDtoConverter = managerDtoConverter;
    }

    @Override
    public ManagerDto assignManager(Long teamId, Long managerId) throws TeamNotFoundException, ManagerNotFoundException, ManagerAlreadyAssignedException, ManagedTeamException {
        Optional<Team> t = teamRepo.findById(teamId);
        if (!t.isPresent()) {
            throw new TeamNotFoundException("validation.error.notFound.team", teamId);
        }

        Optional<Manager> m = managerRepo.findById(managerId);
        if (!m.isPresent()) {
            throw new ManagerNotFoundException("validation.error.notFound.manager", managerId);
        }

        //Any one team can be managed by at most 2 managers
        Integer mangersInTeamCount = managedTeamRepo.countManagedTeamsByTeam_Id(teamId);
        if(mangersInTeamCount >= MAX_ALLOWED_MANAGERS_PER_TEAM){
           throw new ManagedTeamException("validation.error.assigned.managed.teams", teamId, MAX_ALLOWED_MANAGERS_PER_TEAM);
        }

        //A manager cannot be reassigned to a team that they're already assigned to
        if (t.get().getManagedTeams().parallelStream().noneMatch(mt-> managerId.equals(mt.getManager().getId()))) {
            ManagedTeam mt = new ManagedTeam();
            mt.setTeam(t.get());
            mt.setManager(m.get());
            m.get().getManagedTeam().add(mt);

         return managerDtoConverter.convert(managerRepo.save(m.get()));
        } else {
            throw new ManagerAlreadyAssignedException("validation.error.assigned.manager.team", managerId, teamId);
        }
    }
}
