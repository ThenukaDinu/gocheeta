package com.thenuka.gocheetaproject.dto;

import com.thenuka.gocheetaproject.modals.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class JwtResponseDTO {
    String jwtToken;
    UserRoleDTO user;

    public JwtResponseDTO(String jwtToken, UserRoleDTO user) {
        this.jwtToken = jwtToken;
        this.user = user;
    }
}
