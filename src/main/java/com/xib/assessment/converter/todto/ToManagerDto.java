package com.xib.assessment.converter.todto;

import com.xib.assessment.dto.ManagerDto;
import com.xib.assessment.persistence.model.Manager;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ToManagerDto implements Converter<Manager, ManagerDto> {
    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public ManagerDto convert(Manager source) {
        if (Objects.nonNull(source)) {
            return new ManagerDto(source.getId(), source.getFirstName(), source.getLastName(), source.getEmail());
        }
        return null;
    }
}
