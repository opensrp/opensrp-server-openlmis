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

    // TODO: maybe add update endpoint
    public void add(Orderable orderable) {
        repository.add(orderable);
    }
}
