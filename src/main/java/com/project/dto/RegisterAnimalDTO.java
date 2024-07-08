package com.project.dto;

import com.project.entitys.Usuario;
import com.project.enums.ServicePet;

public record RegisterAnimalDTO(
     String name,
     Integer age,
     String race,
     String specie,
     Usuario responsible,
     ServicePet servicePet) {
}
    
