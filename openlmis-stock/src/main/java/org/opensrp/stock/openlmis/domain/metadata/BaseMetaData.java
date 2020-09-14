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
        @JsonSubTypes.Type(value = ReasonMetaData.class, name = "reason"),
        @JsonSubTypes.Type(value = FacilityProgramMetaData.class, name = "facility_program"),
        @JsonSubTypes.Type(value = ValidDestinationMetaData.class, name = "valid_destination"),
        @JsonSubTypes.Type(value = ValidSourceMetaData.class, name = "valid_source") })
@JsonIgnoreProperties({"type"})

public class BaseMetaData {

    @JsonProperty
    private String id;

    @JsonProperty
    private long serverVersion;

    protected BaseMetaData() {}

    public BaseMetaData(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(long serverVersion) {
        this.serverVersion = serverVersion;
    }
}
