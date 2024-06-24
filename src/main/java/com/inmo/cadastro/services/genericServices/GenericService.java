package com.inmo.cadastro.services.genericServices;

import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

public interface GenericService<T, ID extends Serializable> {

    @Transactional
    T save(T entity);

    @Transactional(readOnly = true)
    T findById(ID id);

    @Transactional(readOnly = true)
    List<T> findByAll();

    @Transactional
    void delete(ID id);
}
