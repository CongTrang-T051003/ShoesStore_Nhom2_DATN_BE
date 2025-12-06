package com.fpl.edu.shoeStore.auth.entity;

import java.time.LocalDateTime;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserAuth {

    private int userId;
    private int roleId;
    private String username;
    private String roleName;
    private String passwordHash;
    private String fullName;

   
    private String email;

    private String phone;

   
    private String status; 
  
    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;
    
}
