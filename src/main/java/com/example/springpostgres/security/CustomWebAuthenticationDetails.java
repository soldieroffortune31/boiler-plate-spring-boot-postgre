package com.example.springpostgres.security;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import jakarta.servlet.http.HttpServletRequest;

public class CustomWebAuthenticationDetails extends WebAuthenticationDetails{
    
    private final Integer penggunaId;

    private final String username;

    public CustomWebAuthenticationDetails(HttpServletRequest request, Integer penggunaId, String username) {
        super(request);
        this.penggunaId = penggunaId;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Integer getPenggunaId() {
        return penggunaId;
    }

}
