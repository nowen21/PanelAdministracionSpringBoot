package com.nowensoft.administration.services.admin;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nowensoft.administration.models.admin.Permiso;
import com.nowensoft.administration.models.admin.Role;
import com.nowensoft.administration.models.admin.Usuario;
import com.nowensoft.administration.repositories.admin.PermisoRepository;
import com.nowensoft.administration.repositories.generic.BaseRepository;
import com.nowensoft.administration.services.admin.interfaces.PermisoServiceInterfaz;
import com.nowensoft.administration.services.generic.BaseServicioImpl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional // Asegura que todas las operaciones sean transaccionales
public class PermisoService extends BaseServicioImpl<Permiso, Long> implements PermisoServiceInterfaz {

    @Autowired
    private PermisoRepository permisoRepository;

    @Autowired
    private RoleService roleService;

    public PermisoService(BaseRepository<Permiso, Long> permisoRepository) {
        super(permisoRepository);
    }

    @Override
    protected void validateUniqueFields(Permiso entity, Long currentId) {
        // Verificar si el nuevo codename ya existe en otro registro
        if (!permisoRepository.findByCodename(entity.getCodename()).filter(p -> !p.getId().equals(currentId))
                .isEmpty()) {
            throw new RuntimeException("El codename " + entity.getCodename() + " ya existe.");
        }

        // Verificar si el nuevo nombre ya existe en otro registro
        if (!permisoRepository.findByNombre(entity.getNombre()).filter(p -> !p.getId().equals(currentId)).isEmpty()) {
            throw new RuntimeException("El nombre " + entity.getNombre() + " ya existe.");
        }
    }

    @Override
    protected void updateEntityFields(Permiso existingEntity, Permiso entity) {
        existingEntity.setNombre(entity.getNombre());
        existingEntity.setCodename(entity.getCodename());
    }

    @Override
    protected void validateUniqueFieldsSave(Permiso entity) {

    }

    @Override
    @Transactional
    public Permiso save(Permiso entity) {
        // Guardar el permiso
        Permiso savedPermiso = baseRepository.save(entity);

        // Obtener el rol con ID = 1
        Role role = roleService.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException("Rol con ID 1 no encontrado."));

        // Asignar el permiso al rol
        if (role.getPermisos() == null) {
            role.setPermisos(new HashSet<>());
        }
        role.getPermisos().add(savedPermiso);

        // Guardar el rol actualizado
        roleService.save(role);

        return savedPermiso;
    }
}
