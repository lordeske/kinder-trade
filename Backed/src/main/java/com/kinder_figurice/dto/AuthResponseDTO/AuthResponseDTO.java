package com.kinder_figurice.dto.AuthResponseDTO;


import lombok.Data;

@Data
public class AuthResponseDTO {


    private String accessToken;
    private String tokenType = "Bearer ";


    public AuthResponseDTO(String accessToken)
    {
        this.accessToken= accessToken;
    }



}
