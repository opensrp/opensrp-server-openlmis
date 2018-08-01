package org.opensrp.stock.openlmis.domain.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class TradeItemClassificationMetaData extends BaseMetaData {

    private TradeItemMetaData tradeItem;
    @JsonProperty
    private String classificationSystem;
    @JsonProperty
    private String classificationId;

    public TradeItemClassificationMetaData() { }

    public TradeItemClassificationMetaData(String id, TradeItemMetaData tradeItem, String classificationSystem, String classificationId) {
        super(id);
        this.tradeItem = tradeItem;
        this.classificationSystem = classificationSystem;
        this.classificationId = classificationId;
    }

    /**
     * Constructs a classification for a trade item.
     *
     * @param tradeItem            the trade item this classification belongs to
     * @param classificationSystem the classification system
     * @param classificationId     the id of the classification system
     */
    public TradeItemClassificationMetaData(TradeItemMetaData tradeItem, String classificationSystem,
                                           String classificationId) {
        this.tradeItem = tradeItem;
        this.classificationSystem = classificationSystem;
        this.classificationId = classificationId;
    }


    public TradeItemMetaData getTradeItem() {
        return tradeItem;
    }

    public void setTradeItem(TradeItemMetaData tradeItem) {
        this.tradeItem = tradeItem;
    }

    public String getClassificationSystem() {
        return classificationSystem;
    }

    public void setClassificationSystem(String classificationSystem) {
        this.classificationSystem = classificationSystem;
    }

    public String getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(String classificationId) {
        this.classificationId = classificationId;
    }
}
