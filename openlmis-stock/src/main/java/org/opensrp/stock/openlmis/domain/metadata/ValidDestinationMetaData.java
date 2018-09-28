package org.opensrp.stock.openlmis.domain.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValidDestinationMetaData extends BaseMetaData {

    @JsonProperty
    private String programUuid;
    @JsonProperty
    private String facilityTypeUuid;
    @JsonProperty
    private String facilityName;
    @JsonProperty
    private String openlmisUuid;

    public ValidDestinationMetaData() {}

    public ValidDestinationMetaData(String id, String programUuid, String facilityTypeUuid, String facilityName, String openlmisUuid) {
        super(id);
        this.programUuid = programUuid;
        this.facilityTypeUuid = facilityTypeUuid;
        this.facilityName = facilityName;
        this.openlmisUuid = openlmisUuid;
    }

    public String getProgramUuid() {
        return programUuid;
    }

    public void setProgramUuid(String programUuid) {
        this.programUuid = programUuid;
    }

    public String getFacilityTypeUuid() {
        return facilityTypeUuid;
    }

    public void setFacilityTypeUuid(String facilityTypeUuid) {
        this.facilityTypeUuid = facilityTypeUuid;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getOpenlmisUuid() {
        return openlmisUuid;
    }

    public void setOpenlmisUuid(String openlmisUuid) {
        this.openlmisUuid = openlmisUuid;
    }
}
