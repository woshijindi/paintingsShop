package com.example.paintingshop.enums;


//用途枚举
public enum DemandPurposeEnum {
    PURPOSE_1(1,"Q版"),
    PURPOSE_2(2,"头像"),
    PURPOSE_3(3,"壁纸"),
    PURPOSE_4(4,"自设/OC"),
    PURPOSE_5(5,"其他"),
    ;


    private Integer id;
    private String message;

    DemandPurposeEnum(Integer id, String message) {
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
        for(DemandPurposeEnum demandPurposeEnum:DemandPurposeEnum.values()){
            if(code.equals(demandPurposeEnum.getId())){
                return demandPurposeEnum.getMessage();
            }
        }
        return  null;
    }
}
