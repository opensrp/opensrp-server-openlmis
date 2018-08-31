package org.opensrp.stock.openlmis.domain.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CommodityTypeMetaData extends BaseMetaData {

    @JsonProperty
    private String name;
    @JsonProperty
    private CommodityTypeMetaData parent;
    @JsonProperty
    private String classificationSystem;
    @JsonProperty
    private String classificationId;
    @JsonProperty
    private List<CommodityTypeMetaData> children;
    @JsonProperty
    private List<TradeItemMetaData> tradeItems;

    public CommodityTypeMetaData() {}

    public CommodityTypeMetaData(String id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CommodityTypeMetaData getParent() {
        return parent;
    }

    public void setParent(CommodityTypeMetaData parent) {
        this.parent = parent;
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

    public List<CommodityTypeMetaData> getChildren() {
        return children;
    }

    public void setChildren(List<CommodityTypeMetaData> children) {
        this.children = children;
    }

    public List<TradeItemMetaData> getTradeItems() {
        return tradeItems;
    }

    public void setTradeItems(List<TradeItemMetaData> tradeItems) {
        this.tradeItems = tradeItems;
    }
}
