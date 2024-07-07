package com.inmo.cadastro.services;

import com.inmo.cadastro.models.Usuario;
import com.inmo.cadastro.repository.UsuarioRepository;
import com.inmo.cadastro.services.genericServices.GenericService;
import com.inmo.cadastro.services.genericServices.GenericServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UsuarioService extends GenericServiceImpl<Usuario, Long> implements GenericService<Usuario, Long> {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public CrudRepository<Usuario, Long> getDao() {
        return usuarioRepository;
    }

    public List<Object[]> allUsersData(int est) {
        return usuarioRepository.allUsersData(est);
    }


    public List<Object[]> searchUsersCI(String search, int est) {
        return usuarioRepository.searchUsersCI(search,est);
    }

    public  Usuario findByUsuId(Long id){
        return usuarioRepository.findByUsuId(id);
    }

    public List<Object[]> searchUsersData(String search, int est) {
        return usuarioRepository.searchUsersData(search, est);
    }

    public List<Object[]> getUsersByRol(Long id, int est) {
        return usuarioRepository.getUsersByRol(id,est);
    }

    public int getCantidadUsuarios() {
        return usuarioRepository.cantidadUsuarios();
    }

    public boolean usuarioUnico(String user) {
        int cont = usuarioRepository.usuarioUnico(user.trim());
        System.out.println("user = "+ user+"  count = "+ cont+"\n\n\n\n\n");

        return cont <= 0;
    }

}
