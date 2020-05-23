package com.example.paintingshop.enums;

public enum DemandStateEnum {
    NOT_ACCEPTED(0,"正在招募"),
    ACCEPTED(1,"正在进行"),
    FINISH(2,"已经完成"),
    END(3,"手动终止"),
    ;


    private Integer id;
    private String message;

    DemandStateEnum(Integer id, String message) {
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
