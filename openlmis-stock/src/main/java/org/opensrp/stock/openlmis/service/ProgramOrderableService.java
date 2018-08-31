package org.opensrp.stock.openlmis.service;

import org.opensrp.stock.openlmis.domain.ProgramOrderable;
import org.opensrp.stock.openlmis.repository.ProgramOrderableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramOrderableService {

    @Autowired
    private ProgramOrderableRepository repository;

    public List<ProgramOrderable> getAll() {
        return repository.getAll();
    }

    public List<ProgramOrderable> get(long syncServerVersion) {
        return repository.get(syncServerVersion);
    }

    public void addOrUpdate(ProgramOrderable programOrderable) {
        repository.addOrUpdate(programOrderable);
    }

    public ProgramOrderable get(String id) {
        return repository.get(id);
    }

    public void update(ProgramOrderable programOrderable) { repository.update(programOrderable); }
}
