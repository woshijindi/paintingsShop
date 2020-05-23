package com.example.paintingshop.dto;


import com.example.paintingshop.model.Demand;
import com.example.paintingshop.model.User;
import lombok.Data;

@Data
public class NotificationDTO {

    private Long id;
    private Long notifier;
    private Long receiver;
    private Long outerId;
    private Integer type;
    private Integer state;
    private Long gmtCreate;
    private String typeMessage;
    private User user;
    private Demand demand;
}
