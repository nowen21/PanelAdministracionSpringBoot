package com.nowensoft.administration.repositories.admin;

import org.springframework.stereotype.Repository;

import com.nowensoft.administration.models.admin.Estado;
import com.nowensoft.administration.repositories.generic.BaseRepository;

@Repository
public interface EstadoRepository extends BaseRepository<Estado, Long> {

}
