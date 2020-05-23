package com.example.paintingshop.dto;


import lombok.Data;

@Data
public class DemandCreateDTO {

    private String title;
    private Integer price;
    private String closingDate;
    private String description;
    private Integer specifications;
    private Integer purpose;

}
