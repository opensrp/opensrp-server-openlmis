package org.opensrp.stock.openlmis.service;

import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.metadata.BaseMetaData;
import org.opensrp.stock.openlmis.domain.metadata.CommodityTypeMetaData;
import org.opensrp.stock.openlmis.repository.MasterTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommodityTypeService {

    @Autowired
    private MasterTableRepository repository;

    public List<CommodityTypeMetaData> getAll() {

        List<MasterTableEntry> commodityTypes = repository.get("CommodityType");
        List<CommodityTypeMetaData> commodityTypesMetaData = new ArrayList<>();
        for (MasterTableEntry commodityType : commodityTypes) {
            commodityTypesMetaData.add((CommodityTypeMetaData) commodityType.getJson());
        }
        return commodityTypesMetaData;
    }

    public List<CommodityTypeMetaData> get(long syncServerVersion) {

        List<MasterTableEntry> commodityTypes = repository.get("CommodityType", syncServerVersion);
        List<CommodityTypeMetaData> commodityTypesMetaData = new ArrayList<>();
        for (MasterTableEntry commodityType : commodityTypes) {
            commodityTypesMetaData.add((CommodityTypeMetaData) commodityType.getJson());
        }
        return commodityTypesMetaData;
    }

    // TODO: maybe add update endpoint
    public void add(BaseMetaData entry) {
        repository.add(entry);
    }
}
