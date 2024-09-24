package com.nowensoft.administration.models.admin;

import java.util.Set;

import com.nowensoft.administration.models.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity

@Table(name = "roles")
public class Role extends BaseEntity {

    @Column(nullable = false, unique = true, length = 100)
    private String nombre; // Nombre único del rol (ej. ADMIN, USER)

    @Column(nullable = false, name = "descripcion", length = 255)
    private String descripcion; // La URL que requiere protección

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre != null ? nombre.toUpperCase() : null;
    }

    public Set<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(Set<Permiso> permisos) {
        this.permisos = permisos;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permiso", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permiso_id"))
    private Set<Permiso> permisos; // Permisos asociados al rol

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_ruta", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "ruta_id"))
    private Set<Ruta> rutas; // Permisos asociados al rol

    @ManyToMany(mappedBy = "roles")
    private Set<Usuario> usuarios; // Usuarios asociados al rol

    // Getters y Setters

    public Set<Ruta> getRutas() {
        return rutas;
    }

    public void setRutas(Set<Ruta> rutas) {
        this.rutas = rutas;
    }
}
