package org.opensrp.stock.openlmis.service;

import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.metadata.BaseMetaData;
import org.opensrp.stock.openlmis.domain.metadata.TradeItemClassificationMetaData;
import org.opensrp.stock.openlmis.repository.MasterTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.opensrp.stock.openlmis.util.Utils.TRADE_ITEM_CLASSIFICATION;

@Service
public class TradeItemClassificationService {

    @Autowired
    private MasterTableRepository repository;

    public List<TradeItemClassificationMetaData> getAll() {

        List<MasterTableEntry> tradeItemClassifications = repository.get(TRADE_ITEM_CLASSIFICATION);
        List<TradeItemClassificationMetaData> tradeItemClassificationsMetaData = new ArrayList<>();
        for (MasterTableEntry tradeItemClassification : tradeItemClassifications) {
            tradeItemClassificationsMetaData.add((TradeItemClassificationMetaData) tradeItemClassification.getJson());
        }
        return tradeItemClassificationsMetaData;
    }

    public List<TradeItemClassificationMetaData> get(long syncServerVersion) {

        List<MasterTableEntry> tradeItemClassifications = repository.get(TRADE_ITEM_CLASSIFICATION, syncServerVersion);

        List<TradeItemClassificationMetaData> tradeItemClassificationsMetaData = new ArrayList<>();
        for (MasterTableEntry tradeItemClassification : tradeItemClassifications) {
            tradeItemClassificationsMetaData.add((TradeItemClassificationMetaData) tradeItemClassification.getJson());
        }
        return tradeItemClassificationsMetaData;
    }

    public MasterTableEntry get(String type, String id) {
        return repository.get(type, id);
    }

    public void addOrUpdate(BaseMetaData entry) {
        repository.add(entry);
    }

    public void update(MasterTableEntry entry) { repository.update(entry); }
}
