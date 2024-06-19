package com.project.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.entitys.Usuario;
import com.project.exeptions.RgNotFoundException;
import com.project.repositorys.UserRepository;

@Service
@Transactional(readOnly = true)
public class UsuarioService {

    @Autowired
    private UserRepository userRepository;

    public List<Usuario> searchAllUser() {
        return userRepository.findAll();
    }

    public Usuario findByCpf(String cpf) {
        Usuario usuario = userRepository.findByCpf(cpf);
        if (usuario == null) {
            throw new RgNotFoundException("Não existe usuário com esse CPF: " + cpf);
        }
        return usuario;
    }
    
    public String editRegister(Usuario usuario) {
        userRepository.saveAndFlush(usuario);
        return ("Editado com Sucesso!");
    }

    public void excluir(String id) {
        Usuario usuario = userRepository.findById(id).get();
        userRepository.delete(usuario);
    }

}
