package com.xib.assessment.persistence.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ManagedTeamRepoTest {
    @Autowired
    private ManagedTeamRepo managedTeamRepo;

    @Test
    void findManagedTeamsByTeamEquals() {
    }

    @Test
    void countManagedTeamsByTeam_Id() {
        assertEquals(2, managedTeamRepo.countManagedTeamsByTeam_Id(1L));
    }
}