package com.example.paintingshop.dto;


import com.example.paintingshop.model.Paintings;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {


    private Long id;
    private Long accountId;
    private String name;
    private String password;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
    private String bio;
    private Integer age;
    private Integer identity;
    private String alipayNumber;
    private List<Paintings> paintingsList;


}
