package com.thenuka.gocheetaproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class RoleDTO {
    private String roleName;
    private String description;
    private int roleId;
}
