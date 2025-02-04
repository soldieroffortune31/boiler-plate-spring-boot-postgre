package com.example.springpostgres.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.springpostgres.util.JwtUtil;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        System.out.println("Auth : " + authorizationHeader);

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractSubject(jwt);
                System.out.println("username : " + username);
            } catch (ExpiredJwtException e) {
                // response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                // response.setContentType("apllication/json");
                // response.getWriter().write(objectMapper.writeValueAsString(WebResponse.<String>builder().code(HttpServletResponse.SC_UNAUTHORIZED).message("Token Expired").build()));
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
                return;
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtUtil.validateToken(jwt)) {
                
                Integer penggunaId = (Integer) jwtUtil.extractClaims(jwt, "penggunaId");
                // String subject = jwtUtil.extractSubject(jwt);
            

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username, null, Collections.emptyList());
                // authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                authentication.setDetails(new CustomWebAuthenticationDetails(request, penggunaId, username));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}

