package com.fpl.edu.shoeStore.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthDto {
    private String username;
    private String password;
    private String roleName;
    private Boolean isActive;
    private Boolean isDeleted;
}
