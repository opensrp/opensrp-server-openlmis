package org.opensrp.stock.openlmis.domain;

import org.codehaus.jackson.annotate.JsonProperty;

public class MasterTableMetaData {

    @JsonProperty
    private String id;
    @JsonProperty
    private String type;
    @JsonProperty
    private String identifier;
    @JsonProperty
    private long serverVersion;

    protected  MasterTableMetaData() {}

    public MasterTableMetaData(String id, String type, String identifier, long serverVersion) {
        this.id = id;
        this.type = type;
        this.identifier = identifier;
        this.serverVersion = serverVersion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public long getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(long serverVersion) {
        this.serverVersion = serverVersion;
    }
}
