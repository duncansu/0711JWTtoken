package com.example.demo.service;

import com.example.demo.lib.JWTUtil;
import com.example.demo.model.AuthRequest;
import com.example.demo.model.FinalUser;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<FinalUser> instance = userRepository.findByEmail(email);
        if (instance.isPresent()) {
            return instance.get();
        }
        throw new UsernameNotFoundException("email not found");
    }

    public ResponseEntity<?> getToken(AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            FinalUser user = userRepository.findByEmail(authRequest.getEmail()).get();
            String token = jwtUtil.Sign(user.getId().toString());
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            return ResponseEntity.ok().headers(headers).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}