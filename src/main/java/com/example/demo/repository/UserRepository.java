package com.example.demo.repository;

import com.example.demo.model.FinalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<FinalUser, UUID> {

    public Optional<FinalUser> findByEmail(String email);
}