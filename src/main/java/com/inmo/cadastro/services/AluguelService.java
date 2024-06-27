package com.inmo.cadastro.services;

import com.inmo.cadastro.models.*;
import com.inmo.cadastro.repository.AluguelRepository;
import com.inmo.cadastro.services.genericServices.GenericService;
import com.inmo.cadastro.services.genericServices.GenericServiceImpl;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class AluguelService extends GenericServiceImpl<Aluguel, Long> implements GenericService<Aluguel, Long> {

    private final AluguelRepository aluguelRepository;

    public AluguelService(AluguelRepository aluguelRepository) {
        this.aluguelRepository = aluguelRepository;
    }

    @Override
    public CrudRepository<Aluguel, Long> getDao() {
        return aluguelRepository;
    }
}
