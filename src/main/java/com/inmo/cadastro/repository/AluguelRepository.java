package com.inmo.cadastro.repository;

import com.inmo.cadastro.models.Aluguel;
import com.inmo.cadastro.repository.genericRepository.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AluguelRepository extends GenericRepository<Aluguel, Long> {
}
