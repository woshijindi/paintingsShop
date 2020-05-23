package com.example.paintingshop.dto;


import com.example.paintingshop.model.User;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DemandDTO {
    private Long id;
    private String title;
    private String description;
    private String specifications;
    private String purpose;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer followCount;
    private Integer viewCount;
    private Integer enlistCount;
    private Integer state;
    private String price;
    private String closingDate;
    private String paintingsUrl;
    private User user;
}
