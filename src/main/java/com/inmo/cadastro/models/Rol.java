package com.inmo.cadastro.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rolId")
    private Long rolId;

    @Column(name = "rolNombre")
    private String rolNombre;
    @Column(name = "rolDescripcion")
    private String rolDescripcion;
    @Column(name = "rolFechaRegistro")
    private Timestamp rolFechaRegistro;

    @JsonIgnore
    @OneToMany(mappedBy = "rolId")
    private List<Usuario> listaUsuarios;
}