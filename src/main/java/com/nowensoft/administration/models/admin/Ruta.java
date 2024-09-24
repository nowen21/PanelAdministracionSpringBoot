package com.nowensoft.administration.models.admin;

import java.time.LocalDate;
import java.time.LocalTime;

import com.nowensoft.administration.models.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rutas")
public class Ruta extends BaseEntity {

    @Column(nullable = false, name = "url", length = 100)
    private String url; // La URL que requiere protección
    @Column(nullable = false, name = "descripcion", length = 255)
    private String descripcion; // La URL que requiere protección

    // Getters y Setters

}
