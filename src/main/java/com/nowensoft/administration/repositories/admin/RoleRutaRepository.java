package com.nowensoft.administration.repositories.admin;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nowensoft.administration.models.admin.RoleRuta;
import com.nowensoft.administration.projections.RutaRoleProjection;
import com.nowensoft.administration.repositories.generic.BaseRepository;

@Repository
public interface RoleRutaRepository extends BaseRepository<RoleRuta, Long> {
    @Query("SELECT r.ruta.url AS url, r.role.nombre AS role " +
            "FROM RoleRuta r " +
            "JOIN r.ruta " +
            "JOIN r.role")
    List<RutaRoleProjection> findAllRutasWithRoles();
}
