package com.example.paintingshop.model;

public class Follow {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_follow.id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_follow.heroine_id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Long heroineId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_follow.follower_id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Long followerId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_follow.gmt_create
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Long gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_follow.gmt_modified
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Long gmtModified;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_follow.id
     *
     * @return the value of t_follow.id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_follow.id
     *
     * @param id the value for t_follow.id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_follow.heroine_id
     *
     * @return the value of t_follow.heroine_id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Long getHeroineId() {
        return heroineId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_follow.heroine_id
     *
     * @param heroineId the value for t_follow.heroine_id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setHeroineId(Long heroineId) {
        this.heroineId = heroineId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_follow.follower_id
     *
     * @return the value of t_follow.follower_id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Long getFollowerId() {
        return followerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_follow.follower_id
     *
     * @param followerId the value for t_follow.follower_id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_follow.gmt_create
     *
     * @return the value of t_follow.gmt_create
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_follow.gmt_create
     *
     * @param gmtCreate the value for t_follow.gmt_create
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_follow.gmt_modified
     *
     * @return the value of t_follow.gmt_modified
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Long getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_follow.gmt_modified
     *
     * @param gmtModified the value for t_follow.gmt_modified
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }
}