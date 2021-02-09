package com.xib.assessment.persistence.model;

import lombok.Data;
import org.springframework.data.annotation.Version;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@SequenceGenerator(name = "seq", initialValue = 1, allocationSize = 100)
public abstract class AbstractModelClass implements IModel, Serializable {
    private static final long serialVersionUID = 7547186765622776147L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    @Version
    private Integer version;
    private LocalDateTime dtUpdated;
    private LocalDateTime dtCreated;

    @PreUpdate
    @PrePersist
    public void updateTimeStamps() {
        dtUpdated = LocalDateTime.now();
        if (dtCreated == null) {
            dtCreated = LocalDateTime.now();
        }
    }
}
