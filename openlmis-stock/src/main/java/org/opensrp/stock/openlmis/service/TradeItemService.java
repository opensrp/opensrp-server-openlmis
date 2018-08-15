package org.opensrp.stock.openlmis.service;

import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.metadata.BaseMetaData;
import org.opensrp.stock.openlmis.domain.metadata.TradeItemMetaData;
import org.opensrp.stock.openlmis.repository.MasterTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TradeItemService {

    @Autowired
    private MasterTableRepository repository;

    public List<TradeItemMetaData> getAll() {

        List<MasterTableEntry> tradeItems = repository.get("TradeItem");
        List<TradeItemMetaData> tradeItemsMetaData = new ArrayList<>();
        for (MasterTableEntry tradeItem : tradeItems) {
            tradeItemsMetaData.add((TradeItemMetaData) tradeItem.getJson());
        }
        return tradeItemsMetaData;
    }

    public List<TradeItemMetaData> get(long syncServerVersion) {

        List<MasterTableEntry> tradeItems = repository.get("TradeItem", syncServerVersion);

        List<TradeItemMetaData> tradeItemsMetaData = new ArrayList<>();
        for (MasterTableEntry tradeItem : tradeItems) {
            tradeItemsMetaData.add((TradeItemMetaData) tradeItem.getJson());
        }
        return tradeItemsMetaData;
    }

    public MasterTableEntry get(String type, String id) {
        return repository.get(type, id);
    }

    public void add(BaseMetaData entry) {
        repository.add(entry);
    }

    public void update(MasterTableEntry entry) { repository.update(entry); }
}

