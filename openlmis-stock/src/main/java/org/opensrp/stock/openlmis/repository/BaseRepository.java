package org.opensrp.stock.openlmis.repository;

import java.util.List;

public interface BaseRepository<T> {

    T get(Object id);

    void addOrUpdate(T entity);

    void update(T entity);

    List<T> getAll();

    Long safeRemove(T entity);

}
