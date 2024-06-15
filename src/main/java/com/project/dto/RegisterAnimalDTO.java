package com.project.dto;

import com.project.entitys.Usuario;

public record RegisterAnimalDTO(
     String name,
     Integer age,
     String race,
     String specie,
     Usuario responsible) {
}
    
