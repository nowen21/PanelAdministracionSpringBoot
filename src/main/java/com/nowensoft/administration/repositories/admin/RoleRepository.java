package com.nowensoft.administration.repositories.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nowensoft.administration.dtos.PermisoDTO;
import com.nowensoft.administration.models.admin.Role;
import com.nowensoft.administration.repositories.generic.BaseRepository;

@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {
    Optional<Role> findByNombre(String nombre);

    @Query("SELECT new com.nowensoft.administration.dtos.PermisoDTO(p.id, p.nombre, p.codename) FROM Role r JOIN r.permisos p WHERE r.id = :roleId")
    List<PermisoDTO> findPermisosByRoleId(@Param("roleId") Long roleId);
}
