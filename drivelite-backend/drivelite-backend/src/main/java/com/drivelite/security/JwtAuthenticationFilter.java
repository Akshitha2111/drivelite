package com.drivelite.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtService jwtService;


    public JwtAuthenticationFilter(JwtService jwtService) {

        this.jwtService = jwtService;

    }


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {


        System.out.println("JWT FILTER EXECUTED: " + request.getRequestURI());


        // Get Authorization header
        String authHeader = request.getHeader("Authorization");


        System.out.println("AUTH HEADER: " + authHeader);



        if(authHeader == null || !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }



        String token = authHeader.substring(7);



        String email = jwtService.extractEmail(token);


        System.out.println("EMAIL FROM TOKEN: " + email);



        if(email != null &&
                SecurityContextHolder.getContext()
                        .getAuthentication() == null) {


            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            null
                    );


            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);


            System.out.println("JWT AUTHENTICATION SET SUCCESSFULLY");

        }



        filterChain.doFilter(request, response);

    }

}