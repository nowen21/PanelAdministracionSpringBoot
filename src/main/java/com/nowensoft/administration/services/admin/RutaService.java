package com.nowensoft.administration.services.admin;

import org.springframework.stereotype.Service;

import com.nowensoft.administration.models.admin.Ruta;

import com.nowensoft.administration.repositories.generic.BaseRepository;
import com.nowensoft.administration.services.generic.BaseServicioImpl;

@Service
public class RutaService extends BaseServicioImpl<Ruta, Long> {

    public RutaService(BaseRepository<Ruta, Long> baseRepository) {
        super(baseRepository);
    }

    @Override
    protected void validateUniqueFields(Ruta entity, Long currentId) {

    }

    @Override
    protected void updateEntityFields(Ruta existingEntity, Ruta entity) {

    }

    @Override
    protected void validateUniqueFieldsSave(Ruta entity) {

    }
}
