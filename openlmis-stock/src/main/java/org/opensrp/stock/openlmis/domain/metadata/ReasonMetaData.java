package org.opensrp.stock.openlmis.domain.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReasonMetaData extends BaseMetaData {

    @JsonProperty
    String name;
    @JsonProperty
    ProgramMetaData program;
    @JsonProperty
    String description;
    @JsonProperty
    Boolean additive;

    public ReasonMetaData() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProgramMetaData getProgram() {
        return program;
    }

    public void setProgram(ProgramMetaData program) {
        this.program = program;
    }

    public Boolean getAdditive() {
        return additive;
    }

    public void setAdditive(Boolean additive) {
        this.additive = additive;
    }
}
