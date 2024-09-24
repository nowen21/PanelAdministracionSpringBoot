package com.nowensoft.administration.services.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nowensoft.administration.dtos.PermisoDTO;
import com.nowensoft.administration.models.admin.Role;
import com.nowensoft.administration.models.admin.Usuario;
import com.nowensoft.administration.repositories.admin.RoleRepository;
import com.nowensoft.administration.repositories.generic.BaseRepository;
import com.nowensoft.administration.services.generic.BaseServicioImpl;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
@Transactional // Asegura que todas las operaciones sean transaccionales
public class RoleService extends BaseServicioImpl<Role, Long> {

    @Autowired
    private RoleRepository roleRepository;

    public RoleService(BaseRepository<Role, Long> baseRepository) {
        super(baseRepository);
    }

    @Override
    protected void validateUniqueFields(Role entity, Long currentId) {
        if (!roleRepository.findByNombre(entity.getNombre()).filter(p -> !p.getId().equals(currentId)).isEmpty()) {
            throw new RuntimeException("El nombre " + entity.getNombre() + " ya existe.");
        }

    }

    @Override
    protected void updateEntityFields(Role existingEntity, Role entity) {
        existingEntity.setNombre(entity.getNombre());
    }

     @Override
    protected void validateUniqueFieldsSave(Role entity) {

    }

    public List<PermisoDTO> findPermisosByRoleId(Long roleId) {
        return roleRepository.findPermisosByRoleId(roleId);
    }

}
