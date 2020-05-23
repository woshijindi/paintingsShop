package com.example.paintingshop.enums;

public enum  UserTypeEnum {
    MEMBER(0,"会员"),
    PAINTER(1,"画师"),
    ADMINISTRATORS(2,"管理员");


    private Integer id;
    private String message;

    UserTypeEnum(Integer id, String message) {
        this.id = id;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
