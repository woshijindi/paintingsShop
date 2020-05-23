package com.example.paintingshop.dto;


import lombok.Data;

@Data
public class OrderCreateDTO {
    private Long customerId;
    private Long demandId;
    private Long painterId;
    private Long price;
    private Long enlistId;
}
