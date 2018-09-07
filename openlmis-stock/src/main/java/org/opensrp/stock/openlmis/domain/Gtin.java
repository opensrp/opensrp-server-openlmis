package org.opensrp.stock.openlmis.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

public class Gtin {

    @JsonProperty
    private String gtin;

    private Gtin() {}

    /**
     * Creates a new Gtin value.
     * @param gtin the gtin
     */
    public Gtin(String gtin) throws Exception {
        if (!StringUtils.isNumeric(gtin)) {
            throw new IllegalArgumentException("GTIN should be numeric");
        }
        if (gtin.length() < 8 || gtin.length() > 14) {
            throw new IllegalArgumentException("Invalid GTIN length");
        }
        this.gtin = gtin;
    }

    public String getGtin() {
        return gtin;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    @Override
    public String toString() {
        return gtin;
    }
}

