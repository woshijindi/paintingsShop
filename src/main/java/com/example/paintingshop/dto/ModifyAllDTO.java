package com.example.paintingshop.dto;

import lombok.Data;

@Data
public class ModifyAllDTO {

    private Long userId;
    private String password;
    private String alipayNumber;
    private Integer identity;

}
