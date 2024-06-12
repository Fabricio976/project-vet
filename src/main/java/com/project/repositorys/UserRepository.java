package com.project.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.project.entitys.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, String>  {
    UserDetails findByEmail(String email);
    
    Usuario findByEmailAndCodeRecoveryPassword(String email, String codeRecoveryPassword);
}
