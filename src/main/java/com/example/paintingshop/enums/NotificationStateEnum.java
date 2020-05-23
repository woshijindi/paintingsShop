package com.example.paintingshop.enums;

public enum  NotificationStateEnum {

    NOT_VIEW(0,"未查看"),
    VIEW(1,"已查看");
    ;


    private Integer id;
    private String message;

    NotificationStateEnum(Integer id, String message) {
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
