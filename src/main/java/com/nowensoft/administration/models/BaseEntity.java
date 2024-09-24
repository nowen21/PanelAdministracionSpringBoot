package com.nowensoft.administration.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nowensoft.administration.models.admin.Estado;
import com.nowensoft.administration.models.admin.Usuario;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;

/**
 * Clase base abstracta que representa una entidad en el contexto de JPA.
 * Proporciona propiedades comunes para las entidades, como un identificador
 * único
 * y marcas de tiempo para la creación y actualización.
 * 
 * Esta clase es utilizada como superclase para otras entidades en el sistema,
 * garantizando que todas las entidades tengan un identificador y campos de
 * fecha
 * que se gestionan automáticamente.
 * 
 * @author dumar
 */
@MappedSuperclass

@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity implements Serializable {

    /**
     * Identificador único de la entidad. Este campo es generado automáticamente
     * por la base de datos cuando se crea una nueva entidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuaregi", nullable = true)
    @JsonIgnore
    private Usuario usuaregi;

    @ManyToOne
    @JoinColumn(name = "id_usuamodi", nullable = true)
    @JsonIgnore
    private Usuario usuamodi;

    private LocalDate fechareg;
    private LocalTime horaregi;
    private LocalDate fechamod;
    private LocalTime horamodi;

    @ManyToOne
    @JoinColumn(name = "id_estado", nullable = true)
    @JsonIgnore
    private Estado estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuaregi() {
        return usuaregi;
    }

    public void setUsuaregi(Usuario usuaregi) {
        this.usuaregi = usuaregi;
    }

    public Usuario getUsuamodi() {
        return usuamodi;
    }

    public void setUsuamodi(Usuario usuamodi) {
        this.usuamodi = usuamodi;
    }

    public LocalDate getFechareg() {
        return fechareg;
    }

    public void setFechareg(LocalDate fechareg) {
        this.fechareg = fechareg;
    }

    public LocalTime getHoraregi() {
        return horaregi;
    }

    public void setHoraregi(LocalTime horaregi) {
        this.horaregi = horaregi;
    }

    public LocalDate getFechamod() {
        return fechamod;
    }

    public void setFechamod(LocalDate fechamod) {
        this.fechamod = fechamod;
    }

    public LocalTime getHoramodi() {
        return horamodi;
    }

    public void setHoramodi(LocalTime horamodi) {
        this.horamodi = horamodi;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    // Método que se ejecuta antes de insertar una nueva entidad en la base de datos
    @PrePersist
    protected void onCreate() {
        fechamod = fechareg = LocalDate.now(); // Inicializa la fecha con la fecha actual al momento de crear la entidad
        horamodi = horaregi = LocalTime.now(); // Inicializa la hora con la hora actual al momento de crear la entidad

        Usuario usuario = getCurrentUser();
        if (usuario != null) {
            usuaregi = usuario;
            usuamodi = usuario;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.fechamod = LocalDate.now();
        this.horamodi = LocalTime.now();
        Usuario usuario = getCurrentUser();
        if (usuario != null) {
            usuamodi = usuario;
        }
    }

    // Método para obtener el objeto completo del usuario autenticado
    private Usuario getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof Usuario usuario) {
                return usuario;
            }
        }
        return null; // No hay usuario autenticado o no es del tipo CustomUserDetails
    }

}
