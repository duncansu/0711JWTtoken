package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Register {
    @NonNull
    private String email;
    @NonNull
    private String name;
    @NonNull
    private String password;
}
