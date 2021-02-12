package com.xib.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ManagedTeamDto {
    private Long id;
    private TeamDto team;
    private ManagerDto manager;

}
