package com.project.dto;

import com.project.enums.Role;

public record RegisterDTO(
        String name,
        String email,
        String password,
        Role role,
        String address,
        String telephone) {
}
