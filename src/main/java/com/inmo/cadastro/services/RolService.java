package com.inmo.cadastro.services;

import com.inmo.cadastro.models.Rol;
import com.inmo.cadastro.repository.RolRepository;
import com.inmo.cadastro.services.genericServices.GenericService;
import com.inmo.cadastro.services.genericServices.GenericServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RolService extends GenericServiceImpl<Rol, Long> implements GenericService<Rol, Long> {

    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public CrudRepository<Rol, Long> getDao() {
        return rolRepository;
    }


}
