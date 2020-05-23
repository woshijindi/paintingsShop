package com.example.paintingshop.enums;

public enum PaintingsStyleEnum {
    PAINTINGS_STYLE_1(1,"其他"),
    PAINTINGS_STYLE_2(2,"古风"),
    PAINTINGS_STYLE_3(3,"欧美"),
    PAINTINGS_STYLE_4(4,"日系"),
    ;


    private Integer id;
    private String message;

    PaintingsStyleEnum(Integer id, String message) {
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
        for(PaintingsStyleEnum paintingsStyleEnum:PaintingsStyleEnum.values()){
            if(code.equals(paintingsStyleEnum.getId())){
                return paintingsStyleEnum.getMessage();
            }
        }
        return  null;
    }
}
