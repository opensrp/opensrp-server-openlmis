package org.opensrp.stock.openlmis.service;

import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.metadata.BaseMetaData;
import org.opensrp.stock.openlmis.domain.metadata.CommodityTypeMetaData;
import org.opensrp.stock.openlmis.repository.MasterTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.opensrp.stock.openlmis.util.Utils.COMMODITY_TYPE;

@Service
public class CommodityTypeService {

    @Autowired
    private MasterTableRepository repository;

    public List<CommodityTypeMetaData> getAll() {

        List<MasterTableEntry> commodityTypes = repository.get(COMMODITY_TYPE);
        List<CommodityTypeMetaData> commodityTypesMetaData = new ArrayList<>();
        for (MasterTableEntry commodityType : commodityTypes) {
            commodityTypesMetaData.add((CommodityTypeMetaData) commodityType.getJson());
        }
        return commodityTypesMetaData;
    }

    public List<CommodityTypeMetaData> get(long syncServerVersion) {

        List<MasterTableEntry> commodityTypes = repository.get(COMMODITY_TYPE, syncServerVersion);

        List<CommodityTypeMetaData> commodityTypesMetaData = new ArrayList<>();
        for (MasterTableEntry commodityType : commodityTypes) {
            commodityTypesMetaData.add((CommodityTypeMetaData) commodityType.getJson());
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
