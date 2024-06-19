package com.project.services;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.entitys.Animal;
import com.project.entitys.Usuario;
import com.project.exeptions.CpfNotFoundException;
import com.project.exeptions.RgNotFoundException;
import com.project.repositorys.AnimalRepository;
import com.project.repositorys.UserRepository;

@Service
@Transactional(readOnly = true)
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private UserRepository userRepository;

    private Random random = new Random();

    public List<Animal> searchAllAnimals() {
        return animalRepository.findAll();
    }

    public Animal searchById(String id) {
        return animalRepository.findById(id).get();

    }
    public Animal findByRg(Integer rg) {
        Animal animal = animalRepository.findByRg(rg);
        if (animal == null) {
            throw new RgNotFoundException("Não existe animal cadastrado com esse RG: " + rg);
        }
        return animal;
    }
    
    @Transactional
    public String registerAnimal(String id, Animal animal) {
        if (userRepository.findById(id) == null) {
            throw new CpfNotFoundException("Usuário não encontrado");
        }
        Usuario usuario = userRepository.findById(id).get();
        animal.setResponsible(usuario);
        animal.setDateRegister(new Date());
        animal.setRg(generateUniqueRg());
        animalRepository.save(animal);
        return "Animal Registrado!";
    }

    public String editRegister(Animal animal) {
        animalRepository.saveAndFlush(animal);
        return ("Editado com Sucesso!");
    }

    public void excluir(String id) {
        Animal animal = animalRepository.findById(id).get();
        animalRepository.delete(animal);
    }

    private Integer generateUniqueRg() {
        Integer rg;
        do {
            rg = random.nextInt(90000000) + 10000000; // Gera um número de 8 dígitos
        } while (animalRepository.existsByRg(rg));
        return rg;
    }

}
