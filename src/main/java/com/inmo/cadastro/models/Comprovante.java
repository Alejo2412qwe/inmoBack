package com.inmo.cadastro.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Comprovante")
public class Comprovante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comId;

    @Column(name = "comComprovante", columnDefinition = "LONGTEXT")
    private String comComprovante;

    @Column(name = "comFechaRegistro")
    private Date comFechaRegistro;

    @ManyToOne
    @JoinColumn(name = "aluId", referencedColumnName = "aluId")
    private Aluguel aluId;
}
