package com.inmo.cadastro.services;

import com.inmo.cadastro.models.*;
import com.inmo.cadastro.repository.ComprovanteRepository;
import com.inmo.cadastro.services.genericServices.GenericService;
import com.inmo.cadastro.services.genericServices.GenericServiceImpl;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class ComprovanteService extends GenericServiceImpl<Comprovante, Long> implements GenericService<Comprovante, Long> {

    private final ComprovanteRepository comprovanteRepository;

    public ComprovanteService(ComprovanteRepository comprovanteRepository) {
        this.comprovanteRepository = comprovanteRepository;
    }

    @Override
    public CrudRepository<Comprovante, Long> getDao() {
        return comprovanteRepository;
    }

    public Comprovante getComprovanteById(Long id) {
        return comprovanteRepository.getComprovanteByAluId(id);
    }

    public Comprovante getComprovanteByComFechaRegistro(Long id) {
        return comprovanteRepository.getComprovanteByComFechaRegistro(id);
    }
}
