package com.xib.assessment.service;

import com.xib.assessment.dto.ManagedTeamDto;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TeamManagementServiceTest {
    @MockBean
    private TeamRepo teamRepo;
    @MockBean
    private ManagerRepo managerRepo;
    @MockBean
    private ManagedTeamRepo managedTeamRepo;

    @Autowired
    private TeamManagementService teamManagementService;

    @Test
    @DisplayName("Given: TeamManagementService, When: Assigning Manger To Team, Then: Throw TeamNotFoundException")
    void givenTeamManagementService_whenAssigningManger_thenThrowTeamNotFoundException() {
        Optional<Team> team = Optional.empty();
        when(teamRepo.findById(12L)).thenReturn(team);

        TeamNotFoundException exception = assertThrows(TeamNotFoundException.class, () -> teamManagementService.assignManager(12L, 412L));

        assertEquals("validation.error.notFound.team", exception.getMessage());
        assertEquals(12, exception.getId());
        verify(teamRepo, times(1)).findById(12L);
        verify(managedTeamRepo, times(0)).countManagedTeamsByTeam_Id(12L);
        verify(managerRepo, times(0)).save(any(Manager.class));
    }

    @Test
    @DisplayName("Given: TeamManagementService, When: Assigning Manger To Team, Then: Throw ManagerNotFoundException")
    void givenTeamManagementService_whenAssigningManger_thenManagerNotFoundException() {
        Optional<Team> team = Optional.of(new Team(10L, "Creeck"));
        Optional<Manager> manager = Optional.empty();
        when(teamRepo.findById(12L)).thenReturn(team);
        when(managedTeamRepo.countManagedTeamsByTeam_Id(12L)).thenReturn(2);
        when(managerRepo.findById(412L)).thenReturn(manager);

        ManagerNotFoundException exception = assertThrows(ManagerNotFoundException.class, () -> teamManagementService.assignManager(12L, 412L));

        assertEquals("validation.error.notFound.manager", exception.getMessage());
        assertEquals(412, exception.getId());
        verify(teamRepo, times(1)).findById(12L);
        verify(managerRepo, times(1)).findById(412L);
        verify(managedTeamRepo, times(0)).countManagedTeamsByTeam_Id(12L);
        verify(managerRepo, times(0)).save(any(Manager.class));
    }

    @Test
    @DisplayName("Given: TeamManagementService, When: Assigning Manger To Team, Then: Throw ManagedTeamException")
    void givenTeamManagementService_whenAssigningMangerToTeam_withMax2ManagerAlready_thenThrowManagedTeamException() {
        Optional<Team> team = Optional.of(new Team(321L, "Rado-Redo"));
        Optional<Manager> manager = Optional.of(new Manager(412L, "James", "Jones", "jj@email.com"));
        when(teamRepo.findById(321L)).thenReturn(team);
        when(managerRepo.findById(412L)).thenReturn(manager);
        when(managedTeamRepo.countManagedTeamsByTeam_Id(321L)).thenReturn(2);

        ManagedTeamException exception = assertThrows(ManagedTeamException.class, () -> teamManagementService.assignManager(321L, 412L));

        assertEquals("validation.error.assigned.managed.teams", exception.getMessage());
        assertEquals(321L, exception.getTeamId());
        verify(teamRepo, times(1)).findById(321L);
        verify(managerRepo, times(1)).findById(412L);
        verify(managedTeamRepo, times(1)).countManagedTeamsByTeam_Id(321L);
        verify(managerRepo, times(0)).save(any(Manager.class));
    }

    @Test
    @DisplayName("Given: TeamManagementService, When: Assigning Manger To Team, Then: Throw ManagerAlreadyAssignedException")
    void givenTeamManagementService_whenAssigningManger_thenThrowManagerAlreadyAssignedException() {
        ManagedTeam managedTeam = new ManagedTeam();
        Optional<Team> team = Optional.of(new Team(15L, "Blue"));
        Optional<Manager> manager = Optional.of(new Manager(111L, "Rimbo", "Rums", "rr@email.com"));
        managedTeam.setManager(manager.get());
        team.get().getManagedTeams().add(managedTeam);
        when(teamRepo.findById(15L)).thenReturn(team);
        when(managerRepo.findById(111L)).thenReturn(manager);
        when(managedTeamRepo.countManagedTeamsByTeam_Id(15L)).thenReturn(0);

        ManagerAlreadyAssignedException exception = assertThrows(ManagerAlreadyAssignedException.class, () -> teamManagementService.assignManager(15L, 111L));
        manager.get().getManagedTeam().add(managedTeam);

        assertEquals("validation.error.assigned.manager.team", exception.getMessage());
        assertEquals(15L, exception.getTeamId());
        verify(teamRepo, times(1)).findById(15L);
        verify(managerRepo, times(1)).findById(111L);
        verify(managedTeamRepo, times(1)).countManagedTeamsByTeam_Id(15L);
        verify(managerRepo, times(0)).save(manager.get());
    }

    @Test
    @DisplayName("Given: TeamManagementService, When: Assigning Manger To Team, Then: Return Manager Assigned to Team")
    void givenTeamManagementService_whenAssigningManger_thenReturnManagerAssignedToTeam() throws ManagerAlreadyAssignedException, ManagerNotFoundException, ManagedTeamException, TeamNotFoundException {
        ManagedTeam managedTeam = new ManagedTeam();
        Optional<Team> team = Optional.of(new Team(15L, "Blue"));
        Optional<Manager> manager = Optional.of(new Manager(3L, "Rimbo", "Rums", "rr@email.com"));
        managedTeam.setManager(manager.get());
        managedTeam.setId(1L);
        managedTeam.setTeam(new Team(1L, "New-Team"));
        team.get().getManagedTeams().add(managedTeam);
        manager.get().getManagedTeam().add(managedTeam);

        when(teamRepo.findById(15L)).thenReturn(team);
        when(managerRepo.findById(111L)).thenReturn(manager);
        when(managedTeamRepo.countManagedTeamsByTeam_Id(15L)).thenReturn(0);
        when(managedTeamRepo.save(any(ManagedTeam.class))).thenReturn(managedTeam);

        ManagedTeamDto assignedManager = teamManagementService.assignManager(15L, 111L);

        assertNotNull(assignedManager.getId());
        assertNotNull(assignedManager.getTeam());
        assertEquals(1, assignedManager.getId());
        assertEquals(1, assignedManager.getTeam().getId());
        verify(teamRepo, times(1)).findById(15L);
        verify(managerRepo, times(1)).findById(111L);
        verify(managedTeamRepo, times(1)).countManagedTeamsByTeam_Id(15L);
    }

    @Test
    void findManagedTeamByTeamIdAndManagerId() {
        Team team = new Team(5L, "Drama");
        Manager manager = new Manager(16L, "Dread", "Drock", "dd@gmail.com");
        ManagedTeam managedTeam = new ManagedTeam();
        managedTeam.setManager(manager);
        managedTeam.setTeam(team);
        managedTeam.setId(1L);
        when(managedTeamRepo.findManagedTeamByTeam_IdAndAndManager_Id(5L, 16L)).thenReturn(managedTeam);

        ManagedTeamDto teamResp = teamManagementService.findManagedTeamByTeamIdAndManagerId(5L, 16L);

        assertNotNull(teamResp);
        assertNotNull(teamResp.getManager());
        assertNotNull(teamResp.getTeam());
        assertEquals(5, teamResp.getTeam().getId());
        assertEquals(16, teamResp.getManager().getId());

    }
}