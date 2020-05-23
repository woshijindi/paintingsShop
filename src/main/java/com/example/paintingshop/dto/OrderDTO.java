package com.example.paintingshop.dto;


import com.example.paintingshop.model.Demand;
import com.example.paintingshop.model.Enlist;
import com.example.paintingshop.model.Evaluate;
import com.example.paintingshop.model.User;
import lombok.Data;

@Data

public class OrderDTO {

    private Long id;
    private String orderNumber;
    private Long demandId;
    private Long painterId;
    private Long customerId;
    private Integer state;
    private Long price;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer gmtPaid;
    private Long enlistId;
    private Demand demand;
    private User user;   //画师
    private Enlist enlist;
    private boolean overtime;
    private Evaluate evaluate;
}
