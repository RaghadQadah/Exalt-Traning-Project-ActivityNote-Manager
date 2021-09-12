package com.exalt.app.service.crud;

import java.util.List;

public interface ICurdService<E extends  Object, ID extends Object> {

    public List<E> getAll();

    public E getById(ID id);

    public E saveOrUpdate(E activity);

    public void deleteById(ID ID);


}
