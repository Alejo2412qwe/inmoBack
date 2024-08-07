package com.inmo.cadastro.services;

import com.inmo.cadastro.models.*;
import com.inmo.cadastro.repository.AluguelRepository;
import com.inmo.cadastro.services.genericServices.GenericService;
import com.inmo.cadastro.services.genericServices.GenericServiceImpl;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    public Aluguel getAluguelByInquilino(Long id) {
        return aluguelRepository.getAluguelByInquilino(id);
    }

    public List<Aluguel> getAluguelByPropietario(Long id) {
        return aluguelRepository.getAluguelByPropietario(id);
    }

    public Aluguel findByAluId(Long id) {
        return aluguelRepository.findByAluId(id);
    }

    public boolean inquilinoUnico(Long id) {
        int cont = aluguelRepository.inquilinoUnico(id);
        return cont <= 0;
    }

    public List<Aluguel> allAlugueisData(int est){
        return aluguelRepository.allAlugueisData(est);
    }

    public Double getSumaValores(int est){
        return aluguelRepository.getSumaValores(est);
    }

    public int getCantidadAluguels() {
        return aluguelRepository.cantidadAluguels();
    }

    public List<Object[]> searchAluguelData(String search, int est) {
        return aluguelRepository.searchAluguelData(search, est);
    }
}
