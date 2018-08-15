package org.opensrp.stock.openlmis.service;

import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.metadata.BaseMetaData;
import org.opensrp.stock.openlmis.domain.metadata.ReasonMetaData;
import org.opensrp.stock.openlmis.repository.MasterTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReasonService {

    @Autowired
    private MasterTableRepository repository;

    public List<ReasonMetaData> getAll() {

        List<MasterTableEntry> commodityTypes = repository.get("Reason");
        List<ReasonMetaData> commodityTypesMetaData = new ArrayList<>();
        for (MasterTableEntry commodityType : commodityTypes) {
            commodityTypesMetaData.add((ReasonMetaData) commodityType.getJson());
        }
        return commodityTypesMetaData;
    }

    public List<ReasonMetaData> get(long syncServerVersion) {

        List<MasterTableEntry> commodityTypes = repository.get("Reason", syncServerVersion);

        List<ReasonMetaData> commodityTypesMetaData = new ArrayList<>();
        for (MasterTableEntry commodityType : commodityTypes) {
            commodityTypesMetaData.add((ReasonMetaData) commodityType.getJson());
        }
        return commodityTypesMetaData;
    }

    public MasterTableEntry get(String type, String id) {
        return repository.get(type, id);
    }

    public void add(BaseMetaData entry) {
        repository.add(entry);
    }

    public void update(MasterTableEntry entry) { repository.update(entry); }
}
