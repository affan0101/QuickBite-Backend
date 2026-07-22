package com.quickbite.backend.service;

import com.quickbite.backend.Repository.UserRepository;
import com.quickbite.backend.Security.JwtService;
import com.quickbite.backend.dto.Request.LoginRequest;
import com.quickbite.backend.dto.Request.RegisterRequest;
import com.quickbite.backend.dto.Response.AuthResponse;
import com.quickbite.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest registerRequest){

        User user =User.builder()
                        .name(registerRequest.getName())
                        .email(registerRequest.getEmail())
                        .password(passwordEncoder.encode(registerRequest.getPassword()))
                        .phoneNumber(registerRequest.getPhoneNumber())
                        .role("CUSTOMER")
                        .build();
        userRepository.save(user);

        var jwtToken=jwtService.generateToken(
                new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>()
                ));
        return AuthResponse.builder().token(jwtToken).message("User registered successfully").build();
    }

    public AuthResponse login(LoginRequest loginRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()
        ));

        var user=userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        var jwtToken=jwtService.generateToken(
                new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>())
        );

        return AuthResponse.builder().token(jwtToken).message("Login Successfull").build();

    }
}
