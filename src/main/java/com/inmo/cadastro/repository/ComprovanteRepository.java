package com.inmo.cadastro.repository;

import com.inmo.cadastro.models.*;
import com.inmo.cadastro.repository.genericRepository.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ComprovanteRepository extends GenericRepository<Comprovante, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM comprovante WHERE alu_id = :id")
    Comprovante getComprovanteByAluId(@Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM comprovante " +
            "WHERE MONTH(com_fecha_registro) = MONTH(CURDATE()) " +
            "AND YEAR(com_fecha_registro) = YEAR(CURDATE()) " +
            "AND alu_id = :id")
    Comprovante getComprovanteByComFechaRegistro(@Param("id") Long id);


    @Query(nativeQuery = true, value = "SELECT c.com_id, c.com_comprovante, c.com_fecha_registro, a.alu_id " +
            "FROM comprovante c " +
            "JOIN aluguel a ON c.alu_id = a.alu_id " +
            "JOIN usuario inq ON a.alu_inquilino = inq.usu_id " +
            "WHERE (:dia IS NULL OR DAY(c.com_fecha_registro) = :dia) " +
            "AND (:mes IS NULL OR MONTH(c.com_fecha_registro) = :mes) " +
            "AND (:ano IS NULL OR YEAR(c.com_fecha_registro) = :ano) "
    )
    List<Comprovante> findComprovantesByFechaPartesAndInquilino(@Param("dia") Integer dia, @Param("mes") Integer mes, @Param("ano") Integer ano);


}
