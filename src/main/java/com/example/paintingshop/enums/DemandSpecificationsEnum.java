package com.example.paintingshop.enums;



public enum DemandSpecificationsEnum {
    SPECIFICATIONS_1(1,"3600 x 4800 px (游戏卡牌图)"),
    SPECIFICATIONS_2(2,"宽度 500 px，高度不限 (微博微信配图)"),
    SPECIFICATIONS_3(3,"1920 x 1080 px (高清电脑屏幕)"),
    SPECIFICATIONS_4(4,"216 x 291 mm 含出血 / 300dpi (A4印刷品)"),
    SPECIFICATIONS_5(5,"自定义规格(请自行在描述里描述)"),
    ;


    private Integer id;
    private String message;

    DemandSpecificationsEnum(Integer id, String message) {
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
        for(DemandSpecificationsEnum demandSpecificationsEnum:DemandSpecificationsEnum.values()){
            if(code.equals(demandSpecificationsEnum.getId())){
                return demandSpecificationsEnum.getMessage();
            }
        }
        return  null;
    }
}
