package com.example.demo.controller;

import com.example.demo.lib.JWTUtil;
import com.example.demo.model.AuthRequest;
import com.example.demo.model.FinalUser;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<Map<String,String>> getToken(@RequestBody AuthRequest authRequest) {
            System.out.println(" authRequest :"+authRequest.getEmail());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            FinalUser user = userRepository.findByEmail(authRequest.getEmail()).get();
            String token = jwtUtil.Sign(user.getUuid().toString(),user.getEmail().toString());
            Map<String,String>response= Collections.singletonMap("token",token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String,String>response1= Collections.singletonMap("登入狀態:","失敗，帳號或密碼錯誤");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response1);
        }
    }
}