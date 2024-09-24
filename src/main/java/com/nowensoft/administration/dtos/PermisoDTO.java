package com.nowensoft.administration.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author dumar
 */
@Setter
@Getter
public class PermisoDTO {

    private Long id;
    private String nombre;
    private String codename;

    public PermisoDTO(Long id, String nombre, String codename) {
        this.id = id;
        this.nombre = nombre;
        this.codename = codename;
    }

}
