package org.opensrp.stock.openlmis.domain;

public class Orderable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column core.orderable.id
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column core.orderable.full_product_code
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    private String fullProductCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column core.orderable.full_product_name
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    private String fullProductName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column core.orderable.net_content
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    private Integer netContent;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column core.orderable.pack_rounding_threshold
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    private Integer packRoundingThreshold;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column core.orderable.round_to_zero
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    private Boolean roundToZero;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column core.orderable.dispensable_id
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    private String dispensableId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column core.orderable.trade_item_id
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    private String tradeItemId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column core.orderable.commodity_type_id
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    private String commodityTypeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column core.orderable.use_vvm
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    private Boolean useVvm;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column core.orderable.has_lots
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    private Boolean hasLots;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column core.orderable.server_version
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    private Long serverVersion;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column core.orderable.date_deleted
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    private Long dateDeleted;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column core.orderable.id
     *
     * @return the value of core.orderable.id
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column core.orderable.id
     *
     * @param id the value for core.orderable.id
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column core.orderable.full_product_code
     *
     * @return the value of core.orderable.full_product_code
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    public String getFullProductCode() {
        return fullProductCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column core.orderable.full_product_code
     *
     * @param fullProductCode the value for core.orderable.full_product_code
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    public void setFullProductCode(String fullProductCode) {
        this.fullProductCode = fullProductCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column core.orderable.full_product_name
     *
     * @return the value of core.orderable.full_product_name
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    public String getFullProductName() {
        return fullProductName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column core.orderable.full_product_name
     *
     * @param fullProductName the value for core.orderable.full_product_name
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    public void setFullProductName(String fullProductName) {
        this.fullProductName = fullProductName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column core.orderable.net_content
     *
     * @return the value of core.orderable.net_content
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    public Integer getNetContent() {
        return netContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column core.orderable.net_content
     *
     * @param netContent the value for core.orderable.net_content
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    public void setNetContent(Integer netContent) {
        this.netContent = netContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column core.orderable.pack_rounding_threshold
     *
     * @return the value of core.orderable.pack_rounding_threshold
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    public Integer getPackRoundingThreshold() {
        return packRoundingThreshold;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column core.orderable.pack_rounding_threshold
     *
     * @param packRoundingThreshold the value for core.orderable.pack_rounding_threshold
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    public void setPackRoundingThreshold(Integer packRoundingThreshold) {
        this.packRoundingThreshold = packRoundingThreshold;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column core.orderable.round_to_zero
     *
     * @return the value of core.orderable.round_to_zero
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    public Boolean getRoundToZero() {
        return roundToZero;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column core.orderable.round_to_zero
     *
     * @param roundToZero the value for core.orderable.round_to_zero
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    public void setRoundToZero(Boolean roundToZero) {
        this.roundToZero = roundToZero;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column core.orderable.dispensable_id
     *
     * @return the value of core.orderable.dispensable_id
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    public String getDispensableId() {
        return dispensableId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column core.orderable.dispensable_id
     *
     * @param dispensableId the value for core.orderable.dispensable_id
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    public void setDispensableId(String dispensableId) {
        this.dispensableId = dispensableId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column core.orderable.trade_item_id
     *
     * @return the value of core.orderable.trade_item_id
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    public String getTradeItemId() {
        return tradeItemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column core.orderable.trade_item_id
     *
     * @param tradeItemId the value for core.orderable.trade_item_id
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    public void setTradeItemId(String tradeItemId) {
        this.tradeItemId = tradeItemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column core.orderable.commodity_type_id
     *
     * @return the value of core.orderable.commodity_type_id
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    public String getCommodityTypeId() {
        return commodityTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column core.orderable.commodity_type_id
     *
     * @param commodityTypeId the value for core.orderable.commodity_type_id
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    public void setCommodityTypeId(String commodityTypeId) {
        this.commodityTypeId = commodityTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column core.orderable.use_vvm
     *
     * @return the value of core.orderable.use_vvm
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    public Boolean getUseVvm() {
        return useVvm;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column core.orderable.use_vvm
     *
     * @param useVvm the value for core.orderable.use_vvm
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    public void setUseVvm(Boolean useVvm) {
        this.useVvm = useVvm;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column core.orderable.has_lots
     *
     * @return the value of core.orderable.has_lots
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    public Boolean getHasLots() {
        return hasLots;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column core.orderable.has_lots
     *
     * @param hasLots the value for core.orderable.has_lots
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    public void setHasLots(Boolean hasLots) {
        this.hasLots = hasLots;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column core.orderable.server_version
     *
     * @return the value of core.orderable.server_version
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    public Long getServerVersion() {
        return serverVersion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column core.orderable.server_version
     *
     * @param serverVersion the value for core.orderable.server_version
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    public void setServerVersion(Long serverVersion) {
        this.serverVersion = serverVersion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column core.orderable.date_deleted
     *
     * @return the value of core.orderable.date_deleted
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    public Long getDateDeleted() {
        return dateDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column core.orderable.date_deleted
     *
     * @param dateDeleted the value for core.orderable.date_deleted
     *
     * @mbg.generated Fri Sep 28 11:44:02 EAT 2018
     */
    public void setDateDeleted(Long dateDeleted) {
        this.dateDeleted = dateDeleted;
    }
}