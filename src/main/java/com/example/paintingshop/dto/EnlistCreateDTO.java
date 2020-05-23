package com.example.paintingshop.dto;

import lombok.Data;

@Data
public class EnlistCreateDTO {
    private Long demandId;
    private Long price;
    private String closingDate;
    private String content;
}
