package org.opensrp.stock.openlmis.domain.metadata;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = ProgramMetaData.class, name = "Program"),
        @JsonSubTypes.Type(value = CommodityTypeMetaData.class, name = "CommodityType"),
        @JsonSubTypes.Type(value = TradeItemMetaData.class, name = "TradeItem") })
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
