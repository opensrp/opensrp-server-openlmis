package org.opensrp.stock.openlmis.domain.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TradeItemMetaData extends BaseMetaData {


    @JsonProperty
    private String commodityTypeId;

    @JsonProperty
    private String name;

    @JsonProperty
    private Long netContent;

    public TradeItemMetaData() {}

    public TradeItemMetaData(String id) {
        super(id);
    }

    public String getCommodityTypeId() {
        return commodityTypeId;
    }

    public void setCommodityTypeId(String commodityTypeId) {
        this.commodityTypeId = commodityTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNetContent() {
        return netContent;
    }

    public void setNetContent(Long netContent) {
        this.netContent = netContent;
    }
}
