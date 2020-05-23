package com.example.paintingshop.enums;


public enum ApplyStateEnum {
    PENDING_REVIEW(0,"等待审核"),
    FAIL(1,"未通过"),
    ;


    private Integer id;
    private String message;

    ApplyStateEnum(Integer id, String message) {
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
