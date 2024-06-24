package com.inmo.cadastro.security.Auth;

import com.inmo.cadastro.models.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private Long usuId;
    private String usuNombreUsuario;
    private String usuContrasena;
    private String usuCorreo;
    private int usuEstado;
    private Date usuFechaRegistro;
    private Persona usuPerId;
    private Rol rolId;

}
