package org.opensrp.stock.openlmis.domain;

import org.codehaus.jackson.annotate.JsonProperty;

public class MasterTableMetaData {

    @JsonProperty
    private String uuid;
    @JsonProperty
    private String type;

    protected  MasterTableMetaData() {}

    public MasterTableMetaData(String type, String uuid) {
        this.type = type;
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
