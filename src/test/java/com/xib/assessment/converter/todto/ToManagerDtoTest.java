package com.xib.assessment.converter.todto;

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
class ToManagerDtoTest {
    @Autowired
    private Converter<Manager, ManagerDto> managerConverter;

    @Test
    @DisplayName("ManagerDto Converter - ToManagerDto Component")
    void givenManagerConverter_whenConvertingFromManagerDtoToManager_thenReturnMangerModel() {
        Manager managerDto = new Manager(1L, "NoName", "NoSurname", "no-email@mail.com");
        ManagerDto manager = managerConverter.convert(managerDto);

        assertNotNull(manager);
        assertEquals(1L, manager.getId());
        assertEquals("NoName", manager.getFirstName());
        assertEquals("NoSurname", manager.getLastName());
        assertEquals("no-email@mail.com", manager.getEmail());
    }

    @Test
    @DisplayName("ManagerDto Converter - ToManagerDto Component")
    void givenManagerConverter_withNullParam_whenConvertingFromManagerDtoToManager_thenReturnNull() {
        ManagerDto manager = managerConverter.convert(null);
        assertNull(manager);
    }
}