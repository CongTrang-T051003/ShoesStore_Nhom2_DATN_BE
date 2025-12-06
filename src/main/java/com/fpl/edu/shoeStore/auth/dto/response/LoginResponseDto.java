package com.fpl.edu.shoeStore.auth.dto.response;

import lombok.Data;

@Data
public class LoginResponseDto {
    private String accessToken;
    private String refreshToken;
}
