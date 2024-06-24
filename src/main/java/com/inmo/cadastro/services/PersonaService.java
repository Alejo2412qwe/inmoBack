package com.inmo.cadastro.services;

import com.inmo.cadastro.models.Persona;
import com.inmo.cadastro.repository.PersonaRepository;
import com.inmo.cadastro.services.genericServices.GenericService;
import com.inmo.cadastro.services.genericServices.GenericServiceImpl;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonaService extends GenericServiceImpl<Persona, Long> implements GenericService<Persona, Long> {

    public final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Override
    public CrudRepository<Persona, Long> getDao() {
        return personaRepository;
    }


    public boolean cedulaUnica(String ci) {
        int cont = personaRepository.cedulaUnica(ci.trim());

        return cont <= 0;
    }
}
