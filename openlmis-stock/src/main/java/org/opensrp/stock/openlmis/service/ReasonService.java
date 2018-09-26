package org.opensrp.stock.openlmis.service;

import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.metadata.BaseMetaData;
import org.opensrp.stock.openlmis.domain.metadata.ReasonMetaData;
import org.opensrp.stock.openlmis.repository.MasterTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.opensrp.stock.openlmis.util.Utils.REASON;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReasonService {

    @Autowired
    private MasterTableRepository repository;

    public List<ReasonMetaData> getAll() {

        List<MasterTableEntry> commodityTypes = repository.get(REASON);
        List<ReasonMetaData> commodityTypesMetaData = new ArrayList<>();
        for (MasterTableEntry commodityType : commodityTypes) {
            commodityTypesMetaData.add((ReasonMetaData) commodityType.getJson());
        }
        return commodityTypesMetaData;
    }

    public List<ReasonMetaData> getFiltered(String programId, String facilityTypeUuid) {

        List<ReasonMetaData> reasons = getAll();
        List<ReasonMetaData> filteredResult = new ArrayList<>();
        for (ReasonMetaData reason : reasons) {
            if (programId.equals(reason.getProgramId()) && facilityTypeUuid.equals(reason.getFacilityTypeUuid())) {
                filteredResult.add(reason);
            }
        }
        return filteredResult;
    }
    
    public List<ReasonMetaData> get(long syncServerVersion) {

        List<MasterTableEntry> commodityTypes = repository.get(REASON, syncServerVersion);

        List<ReasonMetaData> commodityTypesMetaData = new ArrayList<>();
        for (MasterTableEntry commodityType : commodityTypes) {
            commodityTypesMetaData.add((ReasonMetaData) commodityType.getJson());
        }
        return commodityTypesMetaData;
    }

    public MasterTableEntry get(String type, String id) {
        return repository.get(type, id);
    }

    public void addOrUpdate(BaseMetaData entry) {
        repository.add(entry);
    }

    public void update(MasterTableEntry entry) {
        repository.update(entry);
    }
}
