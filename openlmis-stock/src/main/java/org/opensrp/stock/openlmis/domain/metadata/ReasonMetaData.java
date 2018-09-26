package org.opensrp.stock.openlmis.domain.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.opensrp.stock.openlmis.domain.StockCardLineItemReason;

public class ReasonMetaData extends BaseMetaData {

    @JsonProperty
    private String programId;
    @JsonProperty
    private String facilityTypeUuid;
    @JsonProperty
    private StockCardLineItemReason stockCardLineItemReason;

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getFacilityTypeUuid() {
        return facilityTypeUuid;
    }

    public void setFacilityTypeUuid(String facilityType) {
        this.facilityTypeUuid = facilityType;
    }

    public StockCardLineItemReason getStockCardLineItemReason() {
        return stockCardLineItemReason;
    }

    public void setStockCardLineItemReason(StockCardLineItemReason stockCardLineItemReason) {
        this.stockCardLineItemReason = stockCardLineItemReason;
    }
}
