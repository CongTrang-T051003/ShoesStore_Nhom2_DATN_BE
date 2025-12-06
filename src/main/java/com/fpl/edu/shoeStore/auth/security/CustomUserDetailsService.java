package com.fpl.edu.shoeStore.auth.security;

import com.fpl.edu.shoeStore.auth.dto.UserAuthDto;
import com.fpl.edu.shoeStore.auth.dto.response.UserAuthResponseDto;
import com.fpl.edu.shoeStore.auth.mapper.UserAuthMapper;
import com.fpl.edu.shoeStore.auth.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    // private final UserAuthMapper userAuthMapper;
    private final UserAuthService userAuthService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuthResponseDto userAuthResponseDto = userAuthService.findUserByUserName(username);
        if (userAuthResponseDto == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        String roleName = "ROLE_" + userAuthResponseDto.getRoleName().toUpperCase();

        String status = userAuthResponseDto.getStatus();
        boolean isActive = "ACTIVE".equalsIgnoreCase(status);
        boolean isDeleted = "DELETED".equalsIgnoreCase(status);

        return org.springframework.security.core.userdetails.User
                .withUsername(userAuthResponseDto.getUsername())
                .password(userAuthResponseDto.getPasswordHash())
                .authorities(roleName)
                .accountLocked(!isActive)
                .disabled(isDeleted)
                .build();
    }
}
