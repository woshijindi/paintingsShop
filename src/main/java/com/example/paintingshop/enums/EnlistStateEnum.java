package com.example.paintingshop.enums;

public enum EnlistStateEnum {

    NOT_ACCEPTED(0,"未被接受"),
    ACCEPTED(1,"已被接受"),
    FINISH(2,"已经完成"),
    GIVE_UP(3,"已经放弃"),
    ;

    private Integer id;
    private String message;


    EnlistStateEnum(Integer id, String message) {
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
