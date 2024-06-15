package com.project.controllers;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.AuthenticationDTO;
import com.project.dto.LoginResponseDTO;
import com.project.dto.RegisterDTO;
import com.project.entitys.Usuario;
import com.project.enums.Role;
import com.project.exeptions.EmailNotFoundException;
import com.project.repositorys.UserRepository;
import com.project.services.details.ManagerUser;
import com.project.services.details.TokenService;

@RestController
@RequestMapping("/projectvet")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ManagerUser managerUser;

    @PostMapping("/code-forgot")
    public String recouverCode(@RequestBody Usuario usuario) {
        String email = usuario.getEmail();
        UserDetails foundUser = userRepository.findByEmail(email);
    
        if (foundUser == null) {
            throw new EmailNotFoundException("Email not found: " + email);
        }
    
        return managerUser.solicitarCodigo(email);
    }
    


    @PostMapping("/change-password")
    public String changePassword(@RequestBody Usuario usuario) {
        return managerUser.alterarSenha(usuario);
    }

    /**
     * endpoint para autenticação do usuário e geração de token JWT
     *
     * @param data Dados de autenticação recebidos no corpo da requisição
     * @return ResponseEntity contendo o token JWT e os detalhes do usuário
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        
        // Busca o usuário pelo email para obter o ID
        UserDetails user = userRepository.findByEmail(data.email());
        String userId = ((Usuario) user).getId();
        return ResponseEntity.ok(new LoginResponseDTO(token, userId));
    }

    /**
     * endpoint para registrar um novo cliente.
     *
     * @param data dados de registro recebidos no corpo da requisição
     * @return ResponseEntity indicando o resultado da operação de registro
     */
    @PostMapping("/register/client")
    public ResponseEntity<String> registerClient(@RequestBody @Valid RegisterDTO data) {
        if (this.userRepository.findByEmail(data.email()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Usuario newUser = new Usuario(data.name(), data.email(), encryptedPassword,
                Role.CLIENT,
                data.address(),
                data.telephone());
        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint para registrar um novo funcionario.
     *
     * @param data Dados de registro recebidos no corpo da requisição
     * @return ResponseEntity indicando o resultado da operação de registro
     */
    @PostMapping("/register/funcionario")
    public ResponseEntity<String> registerFuncionario(@RequestBody @Valid RegisterDTO data) {
        if (this.userRepository.findByEmail(data.email()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Usuario newUser = new Usuario(data.name(), data.email(), encryptedPassword,
                Role.MANAGER,
                data.address(),
                data.telephone());
        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }

}
