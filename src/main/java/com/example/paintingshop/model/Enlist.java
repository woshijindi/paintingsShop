package com.example.paintingshop.model;

public class Enlist {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_enlist.id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_enlist.demand_id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Long demandId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_enlist.user_id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Long userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_enlist.closing_date
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private String closingDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_enlist.price
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Long price;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_enlist.content
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private String content;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_enlist.state
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Integer state;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_enlist.gmt_create
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Long gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_enlist.gmt_modified
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Long gmtModified;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_enlist.id
     *
     * @return the value of t_enlist.id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_enlist.id
     *
     * @param id the value for t_enlist.id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_enlist.demand_id
     *
     * @return the value of t_enlist.demand_id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Long getDemandId() {
        return demandId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_enlist.demand_id
     *
     * @param demandId the value for t_enlist.demand_id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setDemandId(Long demandId) {
        this.demandId = demandId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_enlist.user_id
     *
     * @return the value of t_enlist.user_id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_enlist.user_id
     *
     * @param userId the value for t_enlist.user_id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_enlist.closing_date
     *
     * @return the value of t_enlist.closing_date
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public String getClosingDate() {
        return closingDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_enlist.closing_date
     *
     * @param closingDate the value for t_enlist.closing_date
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate == null ? null : closingDate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_enlist.price
     *
     * @return the value of t_enlist.price
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Long getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_enlist.price
     *
     * @param price the value for t_enlist.price
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setPrice(Long price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_enlist.content
     *
     * @return the value of t_enlist.content
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_enlist.content
     *
     * @param content the value for t_enlist.content
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_enlist.state
     *
     * @return the value of t_enlist.state
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Integer getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_enlist.state
     *
     * @param state the value for t_enlist.state
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_enlist.gmt_create
     *
     * @return the value of t_enlist.gmt_create
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_enlist.gmt_create
     *
     * @param gmtCreate the value for t_enlist.gmt_create
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_enlist.gmt_modified
     *
     * @return the value of t_enlist.gmt_modified
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Long getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_enlist.gmt_modified
     *
     * @param gmtModified the value for t_enlist.gmt_modified
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }
}