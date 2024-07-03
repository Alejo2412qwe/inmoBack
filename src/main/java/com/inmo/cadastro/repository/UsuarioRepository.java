package com.inmo.cadastro.repository;

import com.inmo.cadastro.models.Usuario;
import com.inmo.cadastro.repository.genericRepository.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends GenericRepository<Usuario, Long> {

    @Query(value = "SELECT u.usu_id, u.usu_correo, u.usu_estado, u.usu_fecha_registro, u.usu_nombre_usuario, u.rol_id, u.usu_per_id ,u.foto  " +
            "FROM usuario u JOIN persona p ON u.usu_per_id = p.per_id" +
            "  WHERE u.usu_estado= :est  ORDER BY p.per_apellido, p.per_nombre", nativeQuery = true)
    public List<Object[]> allUsersData(@Param("est") int est);

    @Query(nativeQuery = true, value = "SELECT u.usu_id, u.usu_correo, u.usu_estado, u.usu_fecha_registro, u.usu_nombre_usuario, u.rol_id, u.usu_per_id ,u.foto " +
            "FROM usuario u JOIN persona p ON u.usu_per_id = p.per_id" +
            "  WHERE u.usu_estado= :est  " +
            "  AND  (p.per_cedula LIKE CONCAT ('%', :search, '%') " +
            "  OR CONCAT(LOWER(p.per_apellido), ' ', LOWER(p.per_nombre)) LIKE LOWER (CONCAT('%', :search ,'%'))" +
            "  OR CONCAT(LOWER(p.per_nombre), ' ', LOWER(p.per_apellido)) LIKE LOWER (CONCAT('%', :search ,'%'))" +
            "  OR LOWER(u.usu_nombre_usuario) LIKE LOWER (CONCAT('%', :search ,'%'))" +
            "  OR (p.per_telefono LIKE CONCAT ('%', :search, '%'))" +
            ") ORDER BY p.per_apellido, p.per_nombre"
    )
    List<Object[]> searchUsersData(@Param("search") String search, @Param("est") int est);

    @Query(value = "SELECT u.usu_id, u.usu_correo, u.usu_estado, u.usu_fecha_registro, u.usu_nombre_usuario, u.rol_id, u.usu_per_id ,u.foto  " +
            "FROM usuario u JOIN persona p ON u.usu_per_id = p.per_id" +
            "  WHERE u.rol_id= :id  ORDER BY p.per_apellido, p.per_nombre", nativeQuery = true)
    public List<Object[]> getUsersByRol(@Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT u.usu_correo FROM usuario u WHERE u.rol_id = 1")
    List<String> getMailsOfAdmins();

    @Query(nativeQuery = true, value = "SELECT u.usu_correo FROM usuario u WHERE u.rol_id = 4")
    List<String> getMailsOfInquilinos();

    @Query(nativeQuery = true, value = "SELECT u.usu_id, u.usu_estado, u.usu_per_id " +
            "FROM usuario u JOIN persona p ON u.usu_per_id = p.per_id" +
            "  WHERE u.usu_estado= :est  " +
            "  AND  (p.per_cedula LIKE CONCAT ('%', :search, '%') " +
            ") ORDER BY p.per_apellido, p.per_nombre"
    )
    List<Object[]> searchUsersCI(@Param("search") String search, @Param("est") int est);

    Usuario findByUsuId(Long id);

    @Query(value = "SELECT COUNT(*) FROM usuario", nativeQuery = true)
    int cantidadUsuarios();

    @Query(value = "SELECT COUNT(*) FROM usuario WHERE usu_nombre_usuario =:user", nativeQuery = true)
    int usuarioUnico(@Param("user") String user);

    Optional<Usuario> findByUsuNombreUsuario(String username);

}
