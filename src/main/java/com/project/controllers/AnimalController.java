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



    @GetMapping("/searchAll/{idUser}")
    public List<Animal> searchAllanimals(@PathVariable String idUser) throws Exception {
        return animalService.searchAllAnimals();
    }

    @GetMapping("/searchById/{id}")
    public Animal searchById(@PathVariable("id") String animalId) throws Exception {
        return animalRepository.findById(animalId).get();
    }

    // @GetMapping("/searchByRg/{rg}")
    // public Animal searchByResponsible(@PathVariable Integer rg) throws Exception {
    //     return animalService.findByRg(rg);
    // }

    @PostMapping("/register")
    public String registerAnimal(@RequestBody @Valid Animal dataAnimal, Authentication auth) {
        return animalService.registerAnimal(auth.getPrincipal().toString(), dataAnimal);
    }

    @PutMapping("/editAnimal")
    public ResponseEntity<?> alterar(@RequestParam("idanimal") Animal idAnimal) {
        return ResponseEntity.ok().body(animalService.editRegister(idAnimal));
    }

    @DeleteMapping("/excluirAnimal/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") String id) {
        animalService.excluir(id);
        return ResponseEntity.ok().build();
    }


    
}
