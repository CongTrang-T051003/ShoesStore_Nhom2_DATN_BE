package com.fpl.edu.shoeStore.auth.converter;


import com.fpl.edu.shoeStore.auth.dto.response.UserAuthResponseDto;
import com.fpl.edu.shoeStore.auth.entity.UserAuth;

public class UserAuthConverter {
    
    public static UserAuthResponseDto toDto(UserAuth userAuth) {
        if (userAuth == null) {
            return null;
        }
        
        return UserAuthResponseDto.builder()
                .userId(userAuth.getUserId())
                .roleId(userAuth.getRoleId())
                .username(userAuth.getUsername())
                .roleName(userAuth.getRoleName())
                .passwordHash(userAuth.getPasswordHash())
                .fullName(userAuth.getFullName())
                .email(userAuth.getEmail())
                .phone(userAuth.getPhone())
                .status(userAuth.getStatus())
                .createdAt(userAuth.getCreatedAt())
                .updatedAt(userAuth.getUpdatedAt())
                .build();
    }
}
