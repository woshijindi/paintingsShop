package com.example.paintingshop.enums;

public enum OrderStateEnum {
    TO_BE_PAID(0,"待支付"),
    WAIT_SEND(1,"待发货"),
    TO_BE_HARVESTED(2,"待收货"),
    WAIT_EVALUATE(3,"待评价"),
    COMPLETED(4,"已完成"),
    CANCEL(5,"已取消"),
    ;


    private Integer id;
    private String message;

    OrderStateEnum(Integer id, String message) {
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
