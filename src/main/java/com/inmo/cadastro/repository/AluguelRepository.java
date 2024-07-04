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

    @Query(nativeQuery = true,value = "SELECT SUM(alu_valor) FROM aluguel WHERE alu_estado = :est")
    Double getSumaValores(@Param("est") int est);

    @Query(nativeQuery = true, value = "SELECT a.* FROM aluguel a WHERE a.alu_estado = :est")
    public List<Aluguel> allAlugueisData(@Param("est") int est);

    @Query(nativeQuery = true, value = "SELECT a.* FROM aluguel a WHERE a.alu_inquilino = :id")
    Aluguel getAluguelByInquilino(@Param("id") Long id);

    Aluguel findByAluId(Long id);

    @Query(value = "SELECT COUNT(*) FROM aluguel WHERE alu_inquilino =:id", nativeQuery = true)
    int inquilinoUnico(@Param("id") Long id);

    @Query(value = "SELECT COUNT(*) FROM aluguel", nativeQuery = true)
    int cantidadAluguels();

    @Query(nativeQuery = true, value = "SELECT a.alu_id,a.alu_valor, a.alu_endereco, a.alu_propietario, a.alu_inquilino, a.alu_expiracao, a.alu_dia_pago, a.alu_foto_entrada, a.alu_foto_saida, a.alu_contrato, a.alu_comprovante " +
            "FROM aluguel a " +
            "JOIN usuario prop ON a.alu_propietario = prop.usu_id " +
            "JOIN usuario inq ON a.alu_inquilino = inq.usu_id " +
            "JOIN persona p_prop ON prop.usu_per_id = p_prop.per_id " +
            "JOIN persona p_inq ON inq.usu_per_id = p_inq.per_id " +
            "WHERE a.alu_estado = :est " +
            "AND CONCAT(LOWER(p_prop.per_apellido), ' ', LOWER(p_prop.per_nombre)) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR CONCAT(LOWER(p_prop.per_nombre), ' ', LOWER(p_prop.per_apellido)) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR CONCAT(LOWER(p_inq.per_apellido), ' ', LOWER(p_inq.per_nombre)) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR CONCAT(LOWER(p_inq.per_nombre), ' ', LOWER(p_inq.per_apellido)) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR a.alu_endereco LIKE CONCAT ('%', :search, '%') " +
            "ORDER BY a.alu_endereco"
    )
    List<Object[]> searchAluguelData(@Param("search") String search, @Param("est") int est);
}
