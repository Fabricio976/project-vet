package com.project.dto;

import com.project.enums.Role;

public record RegisterUserDTO(
        String name,
        String email,
        String password,
        String cpf,
        Role role,
        String address,
        String telephone) {
}
