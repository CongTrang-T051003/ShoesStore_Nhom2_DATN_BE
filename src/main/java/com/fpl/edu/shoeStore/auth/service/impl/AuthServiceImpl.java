package com.fpl.edu.shoeStore.auth.service.impl;

import com.fpl.edu.shoeStore.auth.dto.request.LoginRequestDto;
import com.fpl.edu.shoeStore.auth.dto.request.RegisterRequestDto;
import com.fpl.edu.shoeStore.auth.dto.response.AuthResult;
import com.fpl.edu.shoeStore.auth.dto.response.LoginResponseDto;
import com.fpl.edu.shoeStore.auth.dto.response.UserAuthResponseDto;
import com.fpl.edu.shoeStore.auth.security.JwtUtil;
import com.fpl.edu.shoeStore.auth.service.AuthService;
import com.fpl.edu.shoeStore.auth.service.UserAuthService;
import com.fpl.edu.shoeStore.common.enums.ErrorCode;
import com.fpl.edu.shoeStore.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtUtil jwtUtil;
    private final UserAuthService userAuthService;

    @Override
    public ResponseEntity<?> login(LoginRequestDto req) {
        String username = req.getUsername();
        String password = req.getPassword();

        if (!userAuthService.checkLoginByUserNameAndPassword(username, password)) {
            return buildErrorResponse(HttpStatus.UNAUTHORIZED, ErrorCode.INVALID_CREDENTIALS);
        }

        Integer roleId = userAuthService.getRoleIdByUserName(username);
        if (roleId == null) {
            return buildErrorResponse(HttpStatus.LOCKED, ErrorCode.ACCOUNT_LOCKED);
        }

        String accessToken = jwtUtil.generateAccessToken(username, roleId);
        String refreshToken = jwtUtil.generateRefreshToken(username, roleId);
        UserAuthResponseDto loggedUser = userAuthService.findUserByUserName(username);

        LoginResponseDto token = new LoginResponseDto();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);

        ResponseCookie cookie = createRefreshTokenCookie(refreshToken);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new AuthResult(token, loggedUser));
    }

    @Override
    public ResponseEntity<?> register(RegisterRequestDto registerRequest) {
        if (userAuthService.existsByUsername(registerRequest.getUsername())) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, ErrorCode.DUPLICATE_USERNAME);
        }

        if (registerRequest.getEmail() != null && userAuthService.existsByEmail(registerRequest.getEmail())) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, ErrorCode.DUPLICATE_EMAIL);
        }

        if (registerRequest.getPhone() != null && userAuthService.existsByPhone(registerRequest.getPhone())) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, ErrorCode.DUPLICATE_PHONE);
        }

        try {
            User newUser = userAuthService.registerUser(registerRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "success", true,
                    "message", ErrorCode.REGISTER_SUCCESS.getMessage(),
                    "data", newUser
            ));
        } catch (Exception e) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.FAILED_TO_CREATE_ACCOUNT);
        }
    }

    @Override
    public ResponseEntity<?> refresh(String token) {
        if (token == null || !jwtUtil.isValid(token)) {
            return buildErrorResponse(HttpStatus.UNAUTHORIZED, ErrorCode.INVALID_REFRESH_TOKEN);
        }

        String username = jwtUtil.getUsernameFromToken(token);
        int roleId = jwtUtil.getRoleIdFromToken(token);
        String newAccessToken = jwtUtil.generateAccessToken(username, roleId);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "accessToken", newAccessToken
        ));
    }

    @Override
    public ResponseEntity<?> logout() {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .path("/api/v1/auth/refresh")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of(
                        "success", true,
                        "message", ErrorCode.LOGOUT_SUCCESS.getMessage()
                ));
    }

    @Override
    public String getUsernameFromAccessToken(String token) {
        return jwtUtil.getUsernameFromToken(token);
    }

    private ResponseEntity<?> buildErrorResponse(HttpStatus status, ErrorCode errorCode) {
        return ResponseEntity.status(status)
                .body(Map.of(
                        "success", false,
                        "message", errorCode.getMessage(),
                        "code", errorCode.getCode()
                ));
    }

    private ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .path("/api/v1/auth/refresh")
                .maxAge(Duration.ofDays(7))
                .build();
    }
}
