package com.example.paintingshop.model;

public class User {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user.id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user.account_id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Long accountId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user.name
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user.password
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user.token
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private String token;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user.gmt_create
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Long gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user.gmt_modified
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Long gmtModified;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user.avatar_url
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private String avatarUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user.bio
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private String bio;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user.age
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Integer age;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user.identity
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private Integer identity;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user.alipay_number
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    private String alipayNumber;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.id
     *
     * @return the value of t_user.id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.id
     *
     * @param id the value for t_user.id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.account_id
     *
     * @return the value of t_user.account_id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.account_id
     *
     * @param accountId the value for t_user.account_id
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.name
     *
     * @return the value of t_user.name
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.name
     *
     * @param name the value for t_user.name
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.password
     *
     * @return the value of t_user.password
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.password
     *
     * @param password the value for t_user.password
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.token
     *
     * @return the value of t_user.token
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public String getToken() {
        return token;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.token
     *
     * @param token the value for t_user.token
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.gmt_create
     *
     * @return the value of t_user.gmt_create
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.gmt_create
     *
     * @param gmtCreate the value for t_user.gmt_create
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.gmt_modified
     *
     * @return the value of t_user.gmt_modified
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Long getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.gmt_modified
     *
     * @param gmtModified the value for t_user.gmt_modified
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.avatar_url
     *
     * @return the value of t_user.avatar_url
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.avatar_url
     *
     * @param avatarUrl the value for t_user.avatar_url
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.bio
     *
     * @return the value of t_user.bio
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public String getBio() {
        return bio;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.bio
     *
     * @param bio the value for t_user.bio
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setBio(String bio) {
        this.bio = bio == null ? null : bio.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.age
     *
     * @return the value of t_user.age
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Integer getAge() {
        return age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.age
     *
     * @param age the value for t_user.age
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.identity
     *
     * @return the value of t_user.identity
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public Integer getIdentity() {
        return identity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.identity
     *
     * @param identity the value for t_user.identity
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user.alipay_number
     *
     * @return the value of t_user.alipay_number
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public String getAlipayNumber() {
        return alipayNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user.alipay_number
     *
     * @param alipayNumber the value for t_user.alipay_number
     *
     * @mbg.generated Sun May 17 10:49:14 CST 2020
     */
    public void setAlipayNumber(String alipayNumber) {
        this.alipayNumber = alipayNumber == null ? null : alipayNumber.trim();
    }
}