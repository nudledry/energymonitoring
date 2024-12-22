package com.grouphoki.energymonitoring.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Tambahkan log di sini
        if (authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            System.out.println("DEBUG: Redirecting to /admin/dashboard");
            response.sendRedirect("/admin/dashboard");
            return;
        }

        if (authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))) {
            System.out.println("DEBUG: Redirecting to /user/dashboard");
            response.sendRedirect("/user/dashboard");
            return;
        }

        System.out.println("DEBUG: Redirecting to /login?error=true");
        response.sendRedirect("/login?error=true");
    }
}