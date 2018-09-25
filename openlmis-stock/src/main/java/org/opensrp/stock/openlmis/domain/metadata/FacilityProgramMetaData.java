package org.opensrp.stock.openlmis.domain.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FacilityProgramMetaData extends  BaseMetaData {

    @JsonProperty
    String openlmisUuid;
    @JsonProperty
    String facilityName;
    @JsonProperty
    String facilityTypeUuid;
    @JsonProperty
    List<ProgramMetaData> supportedPrograms;

    public FacilityProgramMetaData() {}

    public FacilityProgramMetaData(String openlmisUuid, String facilityName, String facilityTypeUuid) {
        this.openlmisUuid = openlmisUuid;
        this.facilityName = facilityName;
        this.facilityTypeUuid = facilityTypeUuid;
    }

    public String getOpenlmisUuid() {
        return openlmisUuid;
    }

    public void setOpenlmisUuid(String openlmisUuid) {
        this.openlmisUuid = openlmisUuid;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getFacilityTypeUuid() {
        return facilityTypeUuid;
    }

    public void setFacilityTypeUuid(String facilityTypeUuid) {
        this.facilityTypeUuid = facilityTypeUuid;
    }

    public List<ProgramMetaData> getSupportedPrograms() {
        return supportedPrograms;
    }

    public void setSupportedPrograms(List<ProgramMetaData> supportedPrograms) {
        this.supportedPrograms = supportedPrograms;
    }
}
