package com.xib.assessment.util;

import com.xib.assessment.dto.ManagerDto;
import com.xib.assessment.persistence.model.Manager;

import java.util.ArrayList;
import java.util.Collection;

public class ManagerStub {

    public static Collection<ManagerDto> getManagersDto() {
        Collection<ManagerDto> managers = new ArrayList<>();
        managers.add(new ManagerDto(null, "John", "Doe", "john-d@test.com"));
        managers.add(new ManagerDto(null, "Alice", "Wonderland", "alice-d@test.com"));
        return managers;
    }


    public static Collection<Manager> getManagers() {
        Collection<Manager> managers = new ArrayList<>();
        managers.add(new Manager(1L, "James", "Bond", "bond@test.com"));
        managers.add(new Manager(2L, "Sandra", "Newton", "newton@test.com"));
        return managers;
    }

    public static ManagerDto getManagerDto() {
        return new ManagerDto(3L, "Terrance", "Snipe", "terr@test.com");
    }

    public static Manager getManager() {
        return new Manager(3L, "Terrance", "Snipe", "terr@test.com");
    }

}
