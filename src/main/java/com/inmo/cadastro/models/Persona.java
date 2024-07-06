package com.inmo.cadastro.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Persona")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long perId;

    @Column(unique = true, name = "perCedula")
    private String perCedula;

    private String perNombre;
    private String perApellido;
    private String perDireccion;
    private String perTelefono;
    private String perRG;
    private String perEstadoCivil;
    private String perNacionalidade;

    @Column(name = "perFechaNacimiento")
    @Temporal(TemporalType.DATE)
    private Date perFechaNacimiento;

    @OneToOne(mappedBy = "usuPerId")
    @JsonIgnore
    private Usuario usuario;

}
