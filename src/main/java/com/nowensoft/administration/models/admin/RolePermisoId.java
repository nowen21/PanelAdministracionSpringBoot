package com.nowensoft.administration.models.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class RolePermisoId {
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "permiso_id")
    private Long permisoId;
}
