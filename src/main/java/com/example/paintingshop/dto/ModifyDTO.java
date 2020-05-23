package com.example.paintingshop.dto;


import lombok.Data;

@Data
public class ModifyDTO {

    private Integer type;      //1是修改简介。2是修改密码。3是修改支付宝
    private String content;

}
