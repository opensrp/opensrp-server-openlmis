package org.opensrp.stock.openlmis.service;

import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.metadata.BaseMetaData;
import org.opensrp.stock.openlmis.domain.metadata.TradeItemMetaData;
import org.opensrp.stock.openlmis.repository.MasterTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.opensrp.stock.openlmis.util.Utils.TRADE_ITEM;

@Service
public class TradeItemService {

    @Autowired
    private MasterTableRepository repository;

    public List<TradeItemMetaData> getAll() {

        List<MasterTableEntry> tradeItems = repository.get(TRADE_ITEM);
        List<TradeItemMetaData> tradeItemsMetaData = new ArrayList<>();
        for (MasterTableEntry tradeItem : tradeItems) {
            tradeItemsMetaData.add((TradeItemMetaData) tradeItem.getJson());
        }
        return tradeItemsMetaData;
    }

    public List<TradeItemMetaData> get(long syncServerVersion) {

        List<MasterTableEntry> tradeItems = repository.get(TRADE_ITEM, syncServerVersion);

        List<TradeItemMetaData> tradeItemsMetaData = new ArrayList<>();
        for (MasterTableEntry tradeItem : tradeItems) {
            tradeItemsMetaData.add((TradeItemMetaData) tradeItem.getJson());
        }
        return tradeItemsMetaData;
    }

    public MasterTableEntry get(String type, String id) {
        return repository.get(type, id);
    }

    public void addOrUpdate(BaseMetaData entry) {
        repository.add(entry);
    }

    public void update(MasterTableEntry entry) { repository.update(entry); }
}

