package org.opensrp.stock.openlmis.service;

import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.metadata.BaseMetaData;
import org.opensrp.stock.openlmis.domain.metadata.DispensableMetaData;
import org.opensrp.stock.openlmis.repository.MasterTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.opensrp.stock.openlmis.util.Utils.DISPENSABLE;

@Service
public class DispensableService {

    @Autowired
    private MasterTableRepository repository;

    public List<DispensableMetaData> getAll() {

        List<MasterTableEntry> dispensables = repository.get(DISPENSABLE);
        List<DispensableMetaData> dispensablesMetaData = new ArrayList<>();
        for (MasterTableEntry dispensable : dispensables) {
            dispensablesMetaData.add((DispensableMetaData) dispensable.getJson());
        }
        return dispensablesMetaData;
    }

    public List<DispensableMetaData> get(long syncServerVersion) {

        List<MasterTableEntry> dispensables = repository.get(DISPENSABLE, syncServerVersion);

        List<DispensableMetaData> dispensablesMetaData = new ArrayList<>();
        for (MasterTableEntry dispensable : dispensables) {
            dispensablesMetaData.add((DispensableMetaData) dispensable.getJson());
        }
        return dispensablesMetaData;
    }

    public MasterTableEntry get(String type, String id) {
        return repository.get(type, id);
    }

    public void addOrUpdate(BaseMetaData entry) {
        repository.add(entry);
    }

    public void update(MasterTableEntry entry) { repository.update(entry); }
}
