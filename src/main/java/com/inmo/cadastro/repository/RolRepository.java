package com.inmo.cadastro.repository;

import com.inmo.cadastro.models.Rol;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends org.springframework.data.jpa.repository.JpaRepository<Rol, Long>, org.springframework.data.jpa.repository.JpaSpecificationExecutor<Rol> {

}
