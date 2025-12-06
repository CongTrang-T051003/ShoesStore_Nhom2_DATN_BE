package com.fpl.edu.shoeStore.auth.handler;

import com.fpl.edu.shoeStore.auth.security.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Custom Authentication Success Handler for Admin Login
 * Generates JWT token and sets it in cookie after successful form-based login
 */
@Component
@RequiredArgsConstructor
public class AdminAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        
        // Get authenticated user
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        
        // Get roleId from authorities
        // Assuming authority format is "ROLE_ADMIN" or similar
        int roleId = extractRoleId(userDetails);
        
        // Generate JWT tokens
        String accessToken = jwtUtil.generateAccessToken(username, roleId);
        String refreshToken = jwtUtil.generateRefreshToken(username, roleId);
        
        // Set accessToken in HttpOnly cookie (for security)
        Cookie accessCookie = new Cookie("accessToken", accessToken);
        accessCookie.setHttpOnly(true);
        accessCookie.setSecure(false); // Set true in production (HTTPS)
        accessCookie.setPath("/");
        accessCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
        response.addCookie(accessCookie);
        
        // Set refreshToken in HttpOnly cookie
        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(false); // Set true in production
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(30 * 24 * 60 * 60); // 30 days
        response.addCookie(refreshCookie);
        
        // Log successful login
        System.out.println("[Admin Login] User '" + username + "' logged in successfully with roleId: " + roleId);
        
        // Redirect to admin dashboard
        response.sendRedirect("/admin");
    }
    
    /**
     * Extract roleId from UserDetails authorities
     * Default to 2 (ADMIN) if "ROLE_ADMIN" is found
     */
    private int extractRoleId(UserDetails userDetails) {
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            String role = authority.getAuthority();
            if ("ROLE_ADMIN".equals(role)) {
                return 2; // Admin roleId
            } else if ("ROLE_USER".equals(role)) {
                return 1; // User roleId
            }
        }
        return 1; // Default to USER
    }
}
