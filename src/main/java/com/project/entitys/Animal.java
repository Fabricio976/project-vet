package com.project.entitys;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.enums.ServicePet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Animal {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private Integer rg;
    private String name;
    private Integer age;
    private String race;
    private String specie;

    @JsonIgnore
    @Temporal(TemporalType.DATE)
    private Date dateRegister;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario responsible;

    private ServicePet servicePet;

}
