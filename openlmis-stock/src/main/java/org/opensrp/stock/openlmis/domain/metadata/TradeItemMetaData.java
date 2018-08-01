package org.opensrp.stock.openlmis.domain.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.opensrp.stock.openlmis.domain.Gtin;

import java.util.List;

public class TradeItemMetaData extends BaseMetaData {

    @JsonProperty
    private Gtin gtin;

    @JsonProperty
    private String manufacturerOfTradeItem;

    @JsonProperty
    private List<TradeItemClassificationMetaData> classifications;

    public TradeItemMetaData() {}

    public TradeItemMetaData(String id) {
        super(id);
    }

    public Gtin getGtin() {
        return gtin;
    }

    public void setGtin(Gtin gtin) {
        this.gtin = gtin;
    }

    public String getManufacturerOfTradeItem() {
        return manufacturerOfTradeItem;
    }

    public void setManufacturerOfTradeItem(String manufacturerOfTradeItem) {
        this.manufacturerOfTradeItem = manufacturerOfTradeItem;
    }

    public List<TradeItemClassificationMetaData> getClassifications() {
        return classifications;
    }

    public void setClassifications(List<TradeItemClassificationMetaData> classifications) {
        this.classifications = classifications;
    }
}
