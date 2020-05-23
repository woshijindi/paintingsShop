package com.example.paintingshop.enums;

public enum  NotificationTypeEnum {
    ENLIST(0,"应征了你的企划"),
    CHOICE_ENLIST(1,"选定了你在企划"),
    DRAW_SEND(2,"已经发货了，快去查看吧"),
    CHAT_INVITATION(3,"邀请你进入"),
    NO_EXAMINE(4,"您的画师申请被驳回，处理人是"),
    SUCCESS(5,"恭喜，您已经成为平台画师！处理人是"),
    ;


    private Integer id;
    private String message;

    NotificationTypeEnum(Integer id, String message) {
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
        for(NotificationTypeEnum notificationTypeEnum:NotificationTypeEnum.values()){
            if(code.equals(notificationTypeEnum.getId())){
                return notificationTypeEnum.getMessage();
            }
        }
        return  null;
    }

}
