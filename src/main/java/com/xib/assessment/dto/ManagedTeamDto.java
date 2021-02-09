package com.xib.assessment.dto;

import com.xib.assessment.persistence.model.Manager;
import com.xib.assessment.persistence.model.Team;
import lombok.Data;

@Data
public class ManagedTeamDto {
    private Long id;
    private Manager manager;
    private Team team;

}
