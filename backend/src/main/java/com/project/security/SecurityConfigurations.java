package com.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * Configurações de segurança para a aplicação, incluindo controle de acesso e gerenciamento de autenticação.
 * Configurações de CSRF, gerenciamento de sessão e autorização de requisições HTTP são configuradas nesta classe.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    
    @Autowired
    SecurityFilter securityFilter;

    /**
    * Configura o filtro de segurança para a aplicação, incluindo a desativação do CSRF e o gerenciamento de sessões sem estado (stateless).
    * Define permissões para rotas específicas e aplica o filtro de segurança customizado.
    *
    * @param httpSecurity Configuração da segurança HTTP.
    * @return A configuração de segurança aplicada.
    * @throws Exception Se ocorrer algum erro na configuração da segurança.
    */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/projectvet/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/projectvet/register/funcionario").hasRole("MANAGER")
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    /**
    * Configura o AuthenticationManager utilizado na aplicação para gerenciar a autenticação de usuários.
    * 
    * @param authenticationConfiguration Configuração de autenticação.
    * @return O AuthenticationManager configurado.
    * @throws Exception Se ocorrer um erro ao configurar o AuthenticationManager.
    */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}