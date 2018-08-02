package org.opensrp.stock.openlmis.domain.metadata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.opensrp.stock.openlmis.domain.Code;

@JsonIgnoreProperties({"type"})
public class ProgramMetaData extends BaseMetaData {

    @JsonProperty
    private Code code;
    @JsonProperty
    private String name;
    @JsonProperty
    private String description;
    @JsonProperty
    private Boolean active;
    @JsonProperty
    private Boolean periodsSkippable;
    @JsonProperty
    private Boolean skipAuthorization;
    @JsonProperty
    private Boolean showNonFullSupplyTab;
    @JsonProperty
    private Boolean enableDatePhysicalStockCountCompleted;

    public ProgramMetaData() {}

    public ProgramMetaData(String id) {
        super(id);
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getPeriodsSkippable() {
        return periodsSkippable;
    }

    public void setPeriodsSkippable(Boolean periodsSkippable) {
        this.periodsSkippable = periodsSkippable;
    }

    public Boolean getSkipAuthorization() {
        return skipAuthorization;
    }

    public void setSkipAuthorization(Boolean skipAuthorization) {
        this.skipAuthorization = skipAuthorization;
    }

    public Boolean getShowNonFullSupplyTab() {
        return showNonFullSupplyTab;
    }

    public void setShowNonFullSupplyTab(Boolean showNonFullSupplyTab) {
        this.showNonFullSupplyTab = showNonFullSupplyTab;
    }

    public Boolean getEnableDatePhysicalStockCountCompleted() {
        return enableDatePhysicalStockCountCompleted;
    }

    public void setEnableDatePhysicalStockCountCompleted(Boolean enableDatePhysicalStockCountCompleted) {
        this.enableDatePhysicalStockCountCompleted = enableDatePhysicalStockCountCompleted;
    }
}
