package org.opensrp.stock.openlmis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public final class StockCardLineItemReason {

    @JsonProperty
    private String name;

    @JsonProperty
    private String description;

    private String reasonType;

    private String reasonCategory;

    private Boolean isFreeTextAllowed;

    private List<String> tags;


    public StockCardLineItemReason() { }

    /**
     * Creates new instance based on data from the domain object.
     */
    public static StockCardLineItemReason newInstance(StockCardLineItemReason domain) {
        StockCardLineItemReason dto = new StockCardLineItemReason();
        return dto;
    }

    public String getReasonType() {
        return reasonType;
    }

    public void setReasonType(String reasonType) {
        this.reasonType = reasonType;
    }

    public String getReasonCategory() {
        return reasonCategory;
    }

    public void setReasonCategory(String reasonCategory) {
        this.reasonCategory = reasonCategory;
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

    public Boolean getFreeTextAllowed() {
        return isFreeTextAllowed;
    }

    public void setFreeTextAllowed(Boolean freeTextAllowed) {
        isFreeTextAllowed = freeTextAllowed;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
