package com.inmo.cadastro.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuId")
    private Long usuId;

    @Basic
    private String usuNombreUsuario;

    @Basic
    private String usuContrasena;

    @Column(name = "usuCorreo")
    private String usuCorreo;

    @Column(name = "foto", columnDefinition = "LONGTEXT")
    private String foto;

    @Column(name = "usuEstado")
    private int usuEstado;

    @Column(name = "usuFechaRegistro")
    private Date usuFechaRegistro;

    @JsonIgnore
    @OneToMany(mappedBy = "aluPropietario")
    private List<Aluguel> listaPropietarios;

    @JsonIgnore
    @OneToMany(mappedBy = "aluInquilino")
    private List<Aluguel> listaInquilinos;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuPerId", referencedColumnName = "perId")
    private Persona usuPerId;

    @ManyToOne
    @JoinColumn(name = "rolId", referencedColumnName = "rolId")
    private Rol rolId;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((rolId.getRolNombre())));
    }

    @Override
    public String getPassword() {
        return this.getUsuContrasena();
    }

    @Override
    public String getUsername() {
        return this.usuNombreUsuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
