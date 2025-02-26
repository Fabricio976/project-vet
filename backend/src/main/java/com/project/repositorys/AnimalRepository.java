package com.project.repositorys;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.entitys.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, String> {
    
    Animal findByRg(Integer rg);
    
    boolean existsByRg(Integer rg);

    @Query("SELECT a FROM Animal a WHERE a.responsible.cpf = :cpf")
    List<Animal> findAnimalsByUserCpf(@Param("cpf") String cpf);

}
