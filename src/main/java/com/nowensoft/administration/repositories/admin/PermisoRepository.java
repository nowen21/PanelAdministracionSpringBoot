package com.nowensoft.administration.repositories.admin;

import java.util.Optional;
import org.springframework.stereotype.Repository;

import com.nowensoft.administration.models.admin.Permiso;
import com.nowensoft.administration.repositories.generic.BaseRepository;

@Repository
public interface PermisoRepository extends BaseRepository<Permiso, Long> {
  Optional<Permiso> findByCodename(String codename);

  Optional<Permiso> findByNombre(String nombre);

}
