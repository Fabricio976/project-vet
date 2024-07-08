package com.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.RegisterAnimalDTO;
import com.project.entitys.Animal;
import com.project.repositorys.AnimalRepository;
import com.project.services.AnimalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/animals")
public class AnimalController {


    @Autowired
    private AnimalService animalService;

    @Autowired
    private AnimalRepository animalRepository;


    @GetMapping("/searchAll")
    public List<Animal> searchAllanimals(Authentication auth) throws Exception {
        return animalService.searchAllAnimals();
    }

    @GetMapping("/searchByUserCpf/{cpf}")
    public List<Animal> searchByResponsible(@PathVariable String cpf) throws Exception {
        return animalRepository.findAnimalsByUserCpf(cpf);
    }

    @GetMapping("/animalRg/{rg}")
    public Animal getAnimaisByUserCpf(Authentication auth, @PathVariable Integer rg) {
        return animalService.findByRg(rg);
    }
    @PostMapping("/register")
    public String registerAnimal(@RequestBody @Valid RegisterAnimalDTO dataAnimal, Authentication auth) {
        return animalService.registerAnimal(auth.getPrincipal().toString(), dataAnimal);
    }

    @PutMapping("/editAnimal")
    public ResponseEntity<?> alterar(@RequestParam("idanimal") Animal idAnimal) {
        return ResponseEntity.ok().body(animalService.editRegister(idAnimal));
    }

    @DeleteMapping("/excluirAnimal/{rg}")
    public ResponseEntity<Void> excluir(@PathVariable("rg") Integer rg) {
        animalService.excluir(rg);
        return ResponseEntity.ok().build();
    }


    
}
