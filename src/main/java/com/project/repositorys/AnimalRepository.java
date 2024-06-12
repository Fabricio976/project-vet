package com.project.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.entitys.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, String> {

    // Encontra os animais por e-mail do responsável
    @Query("SELECT a FROM Animal a WHERE a.responsible.email = :email")
    List<AnimalRepository> findByResponsibleEmail(@Param("email") String email);
}
