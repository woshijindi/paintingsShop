package com.example.paintingshop.enums;



public enum PaintingsTypeEnum {
    PAINTINGS_TYPE_1(1,"其他"),
    PAINTINGS_TYPE_2(2,"场景"),
    PAINTINGS_TYPE_3(3,"Q版"),
    PAINTINGS_TYPE_4(4,"插画"),
    PAINTINGS_TYPE_5(5,"角色"),
    ;


    private Integer id;
    private String message;

    PaintingsTypeEnum(Integer id, String message) {
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
        for(PaintingsTypeEnum paintingsTypeEnum:PaintingsTypeEnum.values()){
            if(code.equals(paintingsTypeEnum.getId())){
                return paintingsTypeEnum.getMessage();
            }
        }
        return  null;
    }


}
