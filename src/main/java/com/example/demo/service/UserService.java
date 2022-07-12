package com.example.demo.service;

import com.example.demo.model.FinalUser;
import com.example.demo.model.Register;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UserService {

    public ResponseEntity<?> createUser(Register register);

    public List<FinalUser> getAllUser();

    public ResponseEntity<?> getOneUser(String id);

}
