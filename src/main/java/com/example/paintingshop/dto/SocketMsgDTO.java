package com.example.paintingshop.dto;

import lombok.Data;

@Data
public class SocketMsgDTO {
    private int type;  //聊天类型0：群聊，1：单聊.
    private String fromUser;  //发送者.
    private String toUser;  //接受者.
    private String msg; //消息

}