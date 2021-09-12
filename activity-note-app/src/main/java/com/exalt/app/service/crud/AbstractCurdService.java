package com.exalt.app.service.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCurdService<E extends Object, ID extends Object> implements ICurdService<E, ID> {

    @Autowired
    protected CrudRepository<E, ID> repository;

    @Override
    public List<E> getAll() {
        List<E> list = new ArrayList<E>();
        repository.findAll().forEach(item -> list.add(item));
        return list;
    }

    //getting a specific record
    @Override
    public E getById(ID id) {
        return repository.findById(id).get();
    }

    @Override
    public E saveOrUpdate(E activity) {
        return repository.save(activity);
    }

    //deleting a specific record
    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }


}
