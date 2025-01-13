package com.kinder_figurice.dto.AuthResponseDTO;


import lombok.Data;

@Data
public class AuthResponseDTO {
    private String accessToken;
    private String refreshToken;

    public AuthResponseDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}

