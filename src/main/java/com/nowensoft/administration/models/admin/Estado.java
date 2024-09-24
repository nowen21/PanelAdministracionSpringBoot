package com.nowensoft.administration.models.admin;

import com.nowensoft.administration.models.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "estados")
public class Estado extends BaseEntity {

    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String nombre; // Nombre descriptivo del estado (ej. Activo, Inactivo)

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
