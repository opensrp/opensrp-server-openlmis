package org.opensrp.stock.openlmis.domain.metadata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)

@JsonSubTypes({ @JsonSubTypes.Type(value = ProgramMetaData.class, name = "program"),
        @JsonSubTypes.Type(value = CommodityTypeMetaData.class, name = "commodity_type"),
        @JsonSubTypes.Type(value = TradeItemMetaData.class, name = "trade_item"),
        @JsonSubTypes.Type(value = DispensableMetaData.class, name = "dispensable"),
        @JsonSubTypes.Type(value = TradeItemClassificationMetaData.class, name = "trade_item_classification"),
        @JsonSubTypes.Type(value = ReasonMetaData.class, name = "reason")})
@JsonIgnoreProperties({"type"})
public class BaseMetaData {

    @JsonProperty
    private String uuid;

    protected BaseMetaData() {}

    public BaseMetaData(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
