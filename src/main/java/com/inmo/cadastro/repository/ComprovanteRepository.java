package com.inmo.cadastro.repository;

import com.inmo.cadastro.models.*;
import com.inmo.cadastro.repository.genericRepository.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComprovanteRepository extends GenericRepository<Comprovante, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM comprovante WHERE alu_id = :id")
    Comprovante getComprovanteByAluId(@Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM comprovante " +
            "WHERE MONTH(com_fecha_registro) = MONTH(CURDATE()) " +
            "AND YEAR(com_fecha_registro) = YEAR(CURDATE()) " +
            "AND alu_id = :id")
    Comprovante getComprovanteByComFechaRegistro(@Param("id") Long id);

}
