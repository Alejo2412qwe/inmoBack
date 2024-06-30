package com.inmo.cadastro.repository;

import com.inmo.cadastro.models.Aluguel;
import com.inmo.cadastro.repository.genericRepository.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AluguelRepository extends GenericRepository<Aluguel, Long> {

    @Query(nativeQuery = true, value = "SELECT alu_expiracao FROM aluguel")
    List<Date> getDates();

    @Query(nativeQuery = true, value = "SELECT a.* FROM aluguel a WHERE a.alu_inquilino = :id")
    Aluguel getAluguelByInquilino( @Param("id") Long id);

    Aluguel findByAluId(Long id);
}
