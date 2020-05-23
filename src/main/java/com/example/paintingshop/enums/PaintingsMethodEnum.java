package com.example.paintingshop.enums;

public enum PaintingsMethodEnum {

    PAINTINGS_METHOD_1(1,"其他"),
    PAINTINGS_METHOD_2(2,"赛璐璐"),
    PAINTINGS_METHOD_3(3,"水彩"),
    PAINTINGS_METHOD_4(4,"写实"),
    PAINTINGS_METHOD_5(5,"厚涂"),
    ;


    private Integer id;
    private String message;

    PaintingsMethodEnum(Integer id, String message) {
        this.id = id;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public static String getValueByCode(Integer code){
        for(PaintingsMethodEnum paintingsMethodEnum:PaintingsMethodEnum.values()){
            if(code.equals(paintingsMethodEnum.getId())){
                return paintingsMethodEnum.getMessage();
            }
        }
        return  null;
    }

}
