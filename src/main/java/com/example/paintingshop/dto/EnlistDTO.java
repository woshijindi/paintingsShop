package com.example.paintingshop.dto;

import com.example.paintingshop.model.Demand;
import com.example.paintingshop.model.Paintings;
import com.example.paintingshop.model.User;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class EnlistDTO {

    private Long id;
    private Long demandId;
    private Long userId;
    private String closingDate;
    private Long price;
    private String content;
    private Integer state;
    private Long gmtCreate;
    private Long gmtModified;
    private User user;
    private List<Paintings> paintingsList;
    private Demand demand;
}
