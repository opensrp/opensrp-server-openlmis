package org.opensrp.stock.openlmis.domain.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.opensrp.stock.openlmis.domain.StockCardLineItemReason;

public class ReasonMetaData extends BaseMetaData {

    @JsonProperty
    private String programId;
    @JsonProperty
    private String facilityType;
    @JsonProperty
    private StockCardLineItemReason stockCardLineItemReason;

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public StockCardLineItemReason getStockCardLineItemReason() {
        return stockCardLineItemReason;
    }

    public void setStockCardLineItemReason(StockCardLineItemReason stockCardLineItemReason) {
        this.stockCardLineItemReason = stockCardLineItemReason;
    }
}
