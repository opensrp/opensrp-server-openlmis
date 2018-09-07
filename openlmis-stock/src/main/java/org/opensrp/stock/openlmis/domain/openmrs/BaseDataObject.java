package org.opensrp.stock.openlmis.domain.openmrs;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
/**
 * The Base Object for keeping audit data for any business entity.
 */
public abstract class BaseDataObject {

    private Date dateCreated;

    private Date dateEdited;

    private Boolean voided = Boolean.FALSE;

    private Date dateVoided;

    private String voidReason;

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }


    public Date getDateEdited() {
        return dateEdited;
    }

    /**
     * The last edited date of the data
     */
    public void setDateEdited(Date dateEdited) {
        this.dateEdited = dateEdited;
    }

    public Boolean getVoided() {
        return voided;
    }

    public void setVoided(Boolean voided) {
        this.voided = voided;
    }

    public Date getDateVoided() {
        return dateVoided;
    }

    public void setDateVoided(Date dateVoided) {
        this.dateVoided = dateVoided;
    }

    public String getVoidReason() {
        return voidReason;
    }

    public void setVoidReason(String voidReason) {
        this.voidReason = voidReason;
    }

    public BaseDataObject withDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public BaseDataObject withDateEdited(Date dateEdited) {
        this.dateEdited = dateEdited;
        return this;
    }

    public BaseDataObject withVoided(Boolean voided) {
        this.voided = voided;
        return this;
    }

    public BaseDataObject withDateVoided(Date dateVoided) {
        this.dateVoided = dateVoided;
        return this;
    }

    public BaseDataObject withVoidReason(String voidReason) {
        this.voidReason = voidReason;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

