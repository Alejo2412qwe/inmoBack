package com.inmo.cadastro.repository;

import com.inmo.cadastro.models.Rol;
import com.inmo.cadastro.repository.genericRepository.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends GenericRepository<Rol, Long> {

}
