package com.project.entitys;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.project.enums.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String cpf;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    private String nome;
    private String address;
    private String telephone;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateShippingCodigo;

    private String codeRecoveryPassword;

    @OneToMany(mappedBy = "responsible")
    private List<Animal> animalsResponsible = new ArrayList<>();

    public Usuario(String nome, String email, String password, Role role,  String address, String telephone) {
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.role = role;
        this.address = address;
        this.telephone = telephone;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("MANAGER"));
    }

    @Override
    public boolean isEnabled() {
        return true; // Verificar se a conta está habilitada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Verificar se as credenciais estão expiradas
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Verificar se a conta está expirada
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Verifica se a conta esta b
    }

}
