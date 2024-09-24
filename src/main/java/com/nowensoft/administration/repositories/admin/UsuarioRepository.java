package com.nowensoft.administration.repositories.admin;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.nowensoft.administration.models.admin.Usuario;
import com.nowensoft.administration.repositories.generic.BaseRepository;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByDocumento(String documento);
}
