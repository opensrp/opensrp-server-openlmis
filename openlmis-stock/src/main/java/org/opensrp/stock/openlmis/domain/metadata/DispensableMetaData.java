package org.opensrp.stock.openlmis.domain.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DispensableMetaData extends BaseMetaData {

    @JsonProperty
    private String keyDispensingUnit;
    @JsonProperty
    private String keySizeCode;
    @JsonProperty
    private String keyRouteOfAdministration;

    public DispensableMetaData() {}

    public DispensableMetaData(String id) {
        super(id);
    }

    public String getKeyDispensingUnit() {
        return keyDispensingUnit;
    }

    public void setKeyDispensingUnit(String keyDispensingUnit) {
        this.keyDispensingUnit = keyDispensingUnit;
    }

    public String getKeySizeCode() {
        return keySizeCode;
    }

    public void setKeySizeCode(String keySizeCode) {
        this.keySizeCode = keySizeCode;
    }

    public String getKeyRouteOfAdministration() {
        return keyRouteOfAdministration;
    }

    public void setKeyRouteOfAdministration(String keyRouteOfAdministration) {
        this.keyRouteOfAdministration = keyRouteOfAdministration;
    }
}
