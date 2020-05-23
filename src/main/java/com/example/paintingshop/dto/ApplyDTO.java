package com.example.paintingshop.dto;

import lombok.Data;

@Data
public class ApplyDTO {

    private Long userId;
    private Integer result;   //1表似乎通过，2表示驳回


}
