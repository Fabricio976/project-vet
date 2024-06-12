package com.project.dto;

import org.springframework.security.core.userdetails.UserDetails;

public record LoginResponseDTO(String token, UserDetails idUser) {}