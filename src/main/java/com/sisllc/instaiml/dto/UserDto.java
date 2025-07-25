package com.sisllc.instaiml.dto;

public record UserDto(
        String id,
        String name,
        String username,
        String password,
        String roles,
    
        String email,
        String phone,

        String firstName,

        String lastName,

        int age,

        String city
) {}

