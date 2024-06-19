package com.project.services.details;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.entitys.Usuario;
import com.project.repositorys.UserRepository;


@Service
public class ManagerUser {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public String solicitarCodigo(String email) {

        Usuario usuario = (Usuario) userRepository.findByEmail(email);
        usuario.setCodeRecoveryPassword(getCodeRecoveryPassword(usuario.getId()));
        usuario.setDateShippingCodigo(new Date());
        userRepository.saveAndFlush(usuario);
        emailService.enviarEmailTexto(usuario.getEmail(), "Código de Recuperação de Senha",
                "Seu código para recuperação de senha é: " + usuario.getCodeRecoveryPassword());
        return "Codigo enviado para o seu e-mail";

    }

    public String alterarSenha(Usuario user) {
        Usuario userBanco = (Usuario) userRepository.findByEmailAndCodeRecoveryPassword(user.getEmail(),
                user.getCodeRecoveryPassword());
        if (userBanco != null) {
            Date diferenca = new Date(new Date().getTime() - userBanco.getDateShippingCodigo().getTime());
            if (diferenca.getTime() / 1000 < 900) {
                userBanco.setPassword(passwordEncoder.encode(user.getPassword()));
                userBanco.setCodeRecoveryPassword(null);
                userRepository.saveAndFlush(userBanco);
                return "Senha alterada com sucesso!";
            } else {
                return "Tempo expirado, solicite um novo código";
            }
        } else {
            return "E-mail ou código de recuperação incorretos ou inesistentes!";
        }
    }

    private String getCodeRecoveryPassword(String id) {

        if (id.length() > 4) {
            id = id.substring(0, 4);
        } else if (id.length() < 4) {
            int diferenca = 4 - id.length();
            StringBuilder idBuilder = new StringBuilder(id);
            for (int i = 0; i < diferenca; i++) {
                idBuilder.append('0');
            }
            id = idBuilder.toString();
        }

        String caracteresPermitidos = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder codigoBuilder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(caracteresPermitidos.length());
            char caractere = caracteresPermitidos.charAt(index);
            codigoBuilder.append(caractere);
        }

        String codigoAleatorio = codigoBuilder.toString();

        return id + codigoAleatorio;
    }
}