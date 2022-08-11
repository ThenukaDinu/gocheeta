package com.thenuka.gocheetaproject.dto;

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
    Long id;
    String userName;
    String email;

    public JwtResponseDTO(String jwtToken, Long id, String userName, String email) {
        this.jwtToken = jwtToken;
        this.id = id;
        this.userName = userName;
        this.email = email;
    }
}
