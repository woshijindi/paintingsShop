package com.example.paintingshop.model;

public class Apply {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_apply.id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_apply.user_id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Long userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_apply.state
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Integer state;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_apply.gmt_create
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Long gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_apply.gmt_modified
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Long gmtModified;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_apply.id
     *
     * @return the value of t_apply.id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_apply.id
     *
     * @param id the value for t_apply.id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_apply.user_id
     *
     * @return the value of t_apply.user_id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_apply.user_id
     *
     * @param userId the value for t_apply.user_id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_apply.state
     *
     * @return the value of t_apply.state
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Integer getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_apply.state
     *
     * @param state the value for t_apply.state
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_apply.gmt_create
     *
     * @return the value of t_apply.gmt_create
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_apply.gmt_create
     *
     * @param gmtCreate the value for t_apply.gmt_create
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_apply.gmt_modified
     *
     * @return the value of t_apply.gmt_modified
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Long getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_apply.gmt_modified
     *
     * @param gmtModified the value for t_apply.gmt_modified
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }
}