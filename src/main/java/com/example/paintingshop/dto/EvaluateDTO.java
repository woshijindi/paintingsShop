package com.example.paintingshop.dto;

import lombok.Data;

@Data
public class EvaluateDTO {

    private Long receiver;
    private Integer receiverType;
    private Long demandId;
    private Long orderId;
    private Long enlistId;
    private String content;


}
