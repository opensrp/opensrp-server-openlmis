package org.opensrp.stock.openlmis.service;

import org.opensrp.stock.openlmis.domain.Lot;
import org.opensrp.stock.openlmis.repository.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LotService {

    @Autowired
    private LotRepository repository;

    public List<Lot> getAll() {
        return repository.getAll();
    }

    public List<Lot> get(long syncServerVersion) {
        return repository.get(syncServerVersion);
    }

    public void add(Lot lot) {
        repository.add(lot);
    }

    public void update(Lot lot) {
        repository.update(lot);
    }
}
