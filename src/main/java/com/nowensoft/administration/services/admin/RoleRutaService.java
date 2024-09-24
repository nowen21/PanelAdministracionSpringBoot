package com.nowensoft.administration.services.admin;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nowensoft.administration.models.admin.RoleRuta;
import com.nowensoft.administration.repositories.admin.RoleRutaRepository;
import com.nowensoft.administration.repositories.generic.BaseRepository;
import com.nowensoft.administration.services.generic.BaseServicioImpl;

@Service
public class RoleRutaService extends BaseServicioImpl<RoleRuta, Long> {

    @Autowired
    private RoleRutaRepository RoleRutaRepository;

    public List<Map<String, String>> getRutasWithRoles() {
        return RoleRutaRepository.findAllRutasWithRoles().stream()
                .map(projection -> Map.of("url", projection.getUrl(), "role", projection.getRole()))
                .collect(Collectors.toList());
    }

    public RoleRutaService(BaseRepository<RoleRuta, Long> baseRepository) {
        super(baseRepository);
    }

    @Override
    protected void validateUniqueFields(RoleRuta entity, Long currentId) {

    }

    @Override
    protected void updateEntityFields(RoleRuta existingEntity, RoleRuta entity) {

    }

     @Override
    protected void validateUniqueFieldsSave(RoleRuta entity) {

    }

}
