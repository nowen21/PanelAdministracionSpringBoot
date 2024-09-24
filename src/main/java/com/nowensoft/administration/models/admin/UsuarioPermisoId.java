package com.nowensoft.administration.models.admin;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class UsuarioPermisoId implements Serializable {

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "permiso_id")
    private Long permisoId;

    // equals() y hashCode()
}
