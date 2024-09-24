package com.nowensoft.administration.models.admin;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nowensoft.administration.models.BaseEntity;

import jakarta.persistence.JoinColumn;

@Entity

@Table(name = "usuarios", uniqueConstraints = { @UniqueConstraint(columnNames = {  "email" }) })
public class Usuario extends BaseEntity implements UserDetails {

    @Column(nullable = false, length = 100)
    private String pnombre; //

    @Column(nullable = true, length = 100)
    private String snombre; //

    @Column(nullable = false, length = 100)
    private String papellido; //

    @Column(nullable = true, length = 100)
    private String sapellido; //

    @Column(nullable = false,  unique = true, length = 12)
    private String documento; //

    @Column(nullable = false, length = 255)
    private String password; // Contraseña encriptada

    @Column(nullable = false, unique = true, length = 255)
    private String email; // Correo electrónico único del usuario

    @Column(nullable = false, name = "is_superuser", columnDefinition = "boolean default false")
    private Long superuser; // Usuario con todos los permisos del sistema
    // @Column(name = "last_login")
    // private java.sql.Timestamp lastLogin; // Último acceso del usuario

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_role", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles; // Roles asociados al usuario

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_permiso", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "permiso_id"))
    private Set<Permiso> permisos; // Permisos asociados directamente al usuario

    // Getters y Setters

    public String getPnombre() {
        return pnombre;
    }

    public void setPnombre(String pnombre) {
        this.pnombre = pnombre;
    }

    public String getSnombre() {
        return snombre;
    }

    public void setSnombre(String snombre) {
        this.snombre = snombre;
    }

    public String getPapellido() {
        return papellido;
    }

    public void setPapellido(String papellido) {
        this.papellido = papellido;
    }

    public String getSapellido() {
        return sapellido;
    }

    public void setSapellido(String sapellido) {
        this.sapellido = sapellido;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(Set<Permiso> permisos) {
        this.permisos = permisos;
    }

    public Long getSuperuser() {
        return superuser;
    }

    public void setSuperuser(Long superuser) {
        this.superuser = superuser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Obtener los permisos de cada rol
        List<GrantedAuthority> authoritiesFromRoles = roles.stream()
                .flatMap(rol -> rol.getPermisos().stream())  // Obtener los permisos de cada rol
                .map(permiso -> new SimpleGrantedAuthority("PERMISSION_"+permiso.getId())) // Convertir permisos a GrantedAuthority
                .collect(Collectors.toList());

        // Obtener los permisos directos del usuario
        List<GrantedAuthority> authoritiesFromUserPermisos = permisos.stream()
                .map(permiso -> new SimpleGrantedAuthority("PERMISSION_"+permiso.getId())) // Convertir permisos del usuario a GrantedAuthority
                .collect(Collectors.toList());

        // Obtener los roles del usuario, con el prefijo "ROLE_"
        List<GrantedAuthority> authoritiesFromRolesNames = roles.stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getId())) // Convertir el nombre del rol a GrantedAuthority
                .collect(Collectors.toList());

        // Combinar todos en una única colección de GrantedAuthority
        return Stream.of(authoritiesFromRoles, authoritiesFromUserPermisos, authoritiesFromRolesNames)
                .flatMap(Collection::stream)  // Combinar todos los streams
                .collect(Collectors.toList()); // Convertirlo a una lista
    }

    @Override
    public String getUsername() {
        return documento;
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
