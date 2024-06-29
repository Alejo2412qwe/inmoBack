package com.inmo.cadastro.models;

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
@Table(name = "Aluguel")
public class Aluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aluId;

    private String aluEndereco;

    @Column(name = "aluFotoEntrada", columnDefinition = "LONGTEXT")
    private String aluFotoEntrada;

    @Column(name = "aluFotoSaida", columnDefinition = "LONGTEXT")
    private String aluFotoSaida;

    @Column(name = "aluContrato", columnDefinition = "LONGTEXT")
    private String aluContrato;

    @Column(name = "aluExpiracao")
    @Temporal(TemporalType.DATE)
    private Date aluExpiracao;

    @ManyToOne
    @JoinColumn(name = "aluPropietario", referencedColumnName = "usuId")
    private Usuario aluPropietario;

    @ManyToOne
    @JoinColumn(name = "aluInquilino", referencedColumnName = "usuId")
    private Usuario aluInquilino;
}
