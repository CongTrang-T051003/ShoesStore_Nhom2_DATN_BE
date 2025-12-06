package com.fpl.edu.shoeStore.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResult {
    private LoginResponseDto token;
    private UserAuthResponseDto user;
}
