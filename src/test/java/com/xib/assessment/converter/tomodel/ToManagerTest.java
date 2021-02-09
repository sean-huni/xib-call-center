package com.xib.assessment.converter.tomodel;

import com.xib.assessment.dto.ManagerDto;
import com.xib.assessment.persistence.model.Manager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.converter.Converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class ToManagerTest {
    @Autowired
    private Converter<ManagerDto, Manager> managerConverter;

    @Test
    @DisplayName("Manager Converter - ToManagerModel Component")
    void givenManagerConverter_whenConvertingFromManagerDtoToManager_thenReturnMangerModel() {
        ManagerDto managerDto = new ManagerDto(1L, "NoName", "NoSurname", "no-email@mail.com");
        Manager manager = managerConverter.convert(managerDto);

        assertNotNull(manager);
        assertEquals(1L, manager.getId());
        assertEquals("NoName", manager.getFirstName());
        assertEquals("NoSurname", manager.getLastName());
        assertEquals("no-email@mail.com", manager.getEmail());
    }

    @Test
    @DisplayName("Manager Converter - ToManagerModel Component")
    void givenManagerConverter_withNullParam_whenConvertingFromManagerDtoToManager_thenReturnNull() {
        Manager manager = managerConverter.convert(null);
        assertNull(manager);
    }
}