package org.opensrp.stock.openlmis.service;

import org.opensrp.stock.openlmis.domain.Orderable;
import org.opensrp.stock.openlmis.repository.OrderableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderableService {

    @Autowired
    private OrderableRepository repository;

    public List<Orderable> getAll() {
        return repository.getAll();
    }

    public List<Orderable> get(long syncServerVersion) {
        return repository.get(syncServerVersion);
    }

    public void addOrUpdate(Orderable orderable) {
        repository.addOrUpdate(orderable);
    }

    public void update(Orderable orderable) {
        repository.update(orderable);
    }
}
