package org.opensrp.stock.openlmis.domain;

import org.codehaus.jackson.annotate.JsonProperty;

public class MasterTableMetaData {

    @JsonProperty
    private String uuid;
    @JsonProperty
    private String type;
    @JsonProperty
    private Long masterTableEntryId;
    @JsonProperty
    private Long serverVersion;

    protected  MasterTableMetaData() {}

    public MasterTableMetaData(String type, String uuid, Long masterTableEntryId, long serverVersion) {
        this.type = type;
        this.uuid = uuid;
        this.masterTableEntryId = masterTableEntryId;
        this.serverVersion = serverVersion;
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

    public long getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(long serverVersion) {
        this.serverVersion = serverVersion;
    }

    public Long getMasterTableEntryId() {
        return masterTableEntryId;
    }

    public void setMasterTableEntryId(Long masterTableEntryId) {
        this.masterTableEntryId = masterTableEntryId;
    }
}
