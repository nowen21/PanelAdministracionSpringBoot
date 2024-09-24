package com.nowensoft.administration.models.admin;

import java.util.Set;

import com.nowensoft.administration.models.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "permisos")

public class Permiso extends BaseEntity {

    @Column(nullable = false, unique = true, length = 100)
    private String nombre; // Nombre descriptivo del permiso (ej. Ver Usuarios)

    @Column(nullable = false, unique = true, length = 100)
    private String codename; // Nombre del permiso utilizado en el código (ej. VIEW_USER)

    public String getNombre() {
        return nombre;
    }

    public String getCodename() {
        return codename;
    }

    @ManyToMany(mappedBy = "permisos")
    private Set<Role> roles; // Roles que tienen este permiso

    @ManyToMany(mappedBy = "permisos")
    private Set<Usuario> usuarios; // Usuarios que tienen este permiso directamente

    // Getters y Setters
    public void setNombre(String nombre) {
        this.nombre = nombre != null ? nombre.toUpperCase() : null;
    }

    public void setCodename(String codename) {
        this.codename = codename != null ? codename.toUpperCase() : null;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

}
