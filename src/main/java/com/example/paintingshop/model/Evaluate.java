package com.example.paintingshop.model;

public class Evaluate {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_evaluate.id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_evaluate.evaluator
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Long evaluator;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_evaluate.receiver
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Long receiver;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_evaluate.receiver_type
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Integer receiverType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_evaluate.content
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private String content;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_evaluate.gmt_create
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Long gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_evaluate.gmt_modified
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Long gmtModified;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_evaluate.demand_id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Long demandId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_evaluate.order_id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Long orderId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_evaluate.enlist_id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Long enlistId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_evaluate.id
     *
     * @return the value of t_evaluate.id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_evaluate.id
     *
     * @param id the value for t_evaluate.id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_evaluate.evaluator
     *
     * @return the value of t_evaluate.evaluator
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Long getEvaluator() {
        return evaluator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_evaluate.evaluator
     *
     * @param evaluator the value for t_evaluate.evaluator
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setEvaluator(Long evaluator) {
        this.evaluator = evaluator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_evaluate.receiver
     *
     * @return the value of t_evaluate.receiver
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Long getReceiver() {
        return receiver;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_evaluate.receiver
     *
     * @param receiver the value for t_evaluate.receiver
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_evaluate.receiver_type
     *
     * @return the value of t_evaluate.receiver_type
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Integer getReceiverType() {
        return receiverType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_evaluate.receiver_type
     *
     * @param receiverType the value for t_evaluate.receiver_type
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setReceiverType(Integer receiverType) {
        this.receiverType = receiverType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_evaluate.content
     *
     * @return the value of t_evaluate.content
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_evaluate.content
     *
     * @param content the value for t_evaluate.content
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_evaluate.gmt_create
     *
     * @return the value of t_evaluate.gmt_create
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_evaluate.gmt_create
     *
     * @param gmtCreate the value for t_evaluate.gmt_create
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_evaluate.gmt_modified
     *
     * @return the value of t_evaluate.gmt_modified
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Long getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_evaluate.gmt_modified
     *
     * @param gmtModified the value for t_evaluate.gmt_modified
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_evaluate.demand_id
     *
     * @return the value of t_evaluate.demand_id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Long getDemandId() {
        return demandId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_evaluate.demand_id
     *
     * @param demandId the value for t_evaluate.demand_id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setDemandId(Long demandId) {
        this.demandId = demandId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_evaluate.order_id
     *
     * @return the value of t_evaluate.order_id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_evaluate.order_id
     *
     * @param orderId the value for t_evaluate.order_id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_evaluate.enlist_id
     *
     * @return the value of t_evaluate.enlist_id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Long getEnlistId() {
        return enlistId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_evaluate.enlist_id
     *
     * @param enlistId the value for t_evaluate.enlist_id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setEnlistId(Long enlistId) {
        this.enlistId = enlistId;
    }
}