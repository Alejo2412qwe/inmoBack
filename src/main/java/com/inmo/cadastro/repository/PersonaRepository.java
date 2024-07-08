package com.inmo.cadastro.repository;

import com.inmo.cadastro.models.Persona;
import com.inmo.cadastro.repository.genericRepository.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends GenericRepository<Persona, Long> {

    @Query(value = "SELECT COUNT(*) FROM `persona` WHERE per_cedula = :ci", nativeQuery = true)
    int cedulaUnica(@Param("ci") String ci);

    @Query(value = "SELECT COUNT(*) FROM `persona` WHERE perrg = :rg", nativeQuery = true)
    int rgUnico(@Param("rg") String rg);
}
