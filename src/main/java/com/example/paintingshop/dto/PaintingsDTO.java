package com.example.paintingshop.dto;


import com.example.paintingshop.model.User;
import lombok.Data;

@Data
public class PaintingsDTO {


    private Long id;
    private Long painterId;
    private String tag;
    private String paintingsUrl;
    private Integer followCount;
    private Long gmtCreate;
    private Long gmtModified;
    private User user;

}
