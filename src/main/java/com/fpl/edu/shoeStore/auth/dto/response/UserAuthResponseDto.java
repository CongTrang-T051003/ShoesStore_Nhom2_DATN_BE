package com.fpl.edu.shoeStore.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserAuthResponseDto {
    private int userId;
    private int roleId;
    private String username;
    private String roleName;
    private String passwordHash;
    private String fullName;
    private String email;
    private String phone;
    private String status;
    
    
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
