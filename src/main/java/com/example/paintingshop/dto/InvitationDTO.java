package com.example.paintingshop.dto;


import lombok.Data;

@Data
public class InvitationDTO {

    private Long notifier;   //通知者
    private Long receiver;  //接收者

}
