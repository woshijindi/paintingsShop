package com.example.paintingshop.enums;



public enum  DemandPriceEnum {
    Under_Twenty(1,"20元/张 以下"),
    Twenty_To_Fifty(2,"20元/张 - 50元/张"),
    Fifty_To_OneHundred(3,"50元/张 - 100元/张"),
    OneHundred_To_TwoHundred(4,"100元/张 - 200元/张"),
    Over_TwoHundred(5,"200元/张 以上"),
    ;


    private Integer id;
    private String message;

    DemandPriceEnum(Integer id, String message) {
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
        for(DemandPriceEnum demandPriceEnum:DemandPriceEnum.values()){
            if(code.equals(demandPriceEnum.getId())){
                return demandPriceEnum.getMessage();
            }
        }
        return  null;
    }

}
