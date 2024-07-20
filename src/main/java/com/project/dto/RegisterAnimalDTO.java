package com.project.dto;

import com.project.enums.ServicePet;

public record RegisterAnimalDTO(
     String name,
     Integer age,
     String race,
     String specie,
     String responsible,
     ServicePet servicePet) {
}
    
