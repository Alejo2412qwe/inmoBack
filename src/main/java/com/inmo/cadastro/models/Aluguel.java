package com.inmo.cadastro.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

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

    private int aluDiaPago;

    /*
    * 0 = Inativo
    * 1 = Ativo
    * */
    private int aluEstado;

    private int aluValor;

    @Column(name = "aluFotoEntrada", columnDefinition = "LONGTEXT")
    private String aluFotoEntrada;

    @Column(name = "aluFotoSaida", columnDefinition = "LONGTEXT")
    private String aluFotoSaida;

    @Column(name = "aluContrato", columnDefinition = "LONGTEXT")
    private String aluContrato;

    @Column(name = "aluContratoCopia", columnDefinition = "LONGTEXT")
    private String aluContratoCopia;

    @Column(name = "aluExpiracao")
    @Temporal(TemporalType.DATE)
    private Date aluExpiracao;

    @ManyToOne
    @JoinColumn(name = "aluPropietario", referencedColumnName = "usuId")
    private Usuario aluPropietario;

    @ManyToOne
    @JoinColumn(name = "aluInquilino", referencedColumnName = "usuId")
    private Usuario aluInquilino;

    @JsonIgnore
    @OneToMany(mappedBy = "aluId")
    private List<Comprovante> listaComprovantes;
}
