package com.nowensoft.administration.services.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.nowensoft.administration.models.admin.Usuario;
import com.nowensoft.administration.repositories.admin.UsuarioRepository;
import com.nowensoft.administration.repositories.generic.BaseRepository;
import com.nowensoft.administration.services.generic.BaseServicioImpl;

/**
 *
 * @author dumar
 */
@Service
@Transactional // Asegura que todas las operaciones sean transaccionales
public class UsuarioService extends BaseServicioImpl<Usuario, Long> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioService(BaseRepository<Usuario, Long> baseRepository) {
        super(baseRepository);
    }

    @Override
    protected void validateUniqueFields(Usuario entity, Long currentId) {
        // Verificar si el nuevo nombre ya existe en otro registro
        if (!usuarioRepository.findByEmail(entity.getEmail()).filter(p -> !p.getId().equals(currentId)).isEmpty()) {
            throw new RuntimeException("El email " + entity.getEmail() + " ya existe.");
        }
        if (!usuarioRepository.findByDocumento(entity.getDocumento()).filter(p -> !p.getId().equals(currentId))
                .isEmpty()) {
            throw new RuntimeException("El documento " + entity.getEmail() + " ya existe.");
        }

    }

    @Override
    protected void validateUniqueFieldsSave(Usuario entity) {

    }

    @Override
    protected void updateEntityFields(Usuario existingEntity, Usuario entity) {
        existingEntity.setPnombre(entity.getPnombre());
        existingEntity.setSnombre(entity.getSnombre());
        existingEntity.setPapellido(entity.getPapellido());
        existingEntity.setSapellido(entity.getSapellido());
        existingEntity.setEmail(entity.getEmail());
        existingEntity.setSuperuser(entity.getSuperuser());
    }

    public boolean hasPermission(String permission) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            return auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(grantedAuthority -> grantedAuthority.equals(permission));
        }
        return false;
    }
}
