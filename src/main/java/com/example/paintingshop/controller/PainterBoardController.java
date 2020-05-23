package com.example.paintingshop.controller;


import com.example.paintingshop.dto.DemandDTO;
import com.example.paintingshop.dto.EnlistDTO;
import com.example.paintingshop.exception.CustomizeErrorCode;
import com.example.paintingshop.exception.CustomizeException;
import com.example.paintingshop.model.User;
import com.example.paintingshop.service.DemandService;
import com.example.paintingshop.service.EnlistService;
import com.example.paintingshop.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller

public class PainterBoardController {

    @Autowired
    private DemandService demandService;
    @Autowired
    private EnlistService enlistService;
    @Autowired
    private FollowService followService;


    @GetMapping("/painterBoard/{id}")
    public String painterBoard(@PathVariable(name = "id") Long id,
                               Model model,
                               HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");  //user
        if (user==null){
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }


        EnlistDTO enlisetDTO = enlistService.getById(id);      //得到当前应征,其中的User是企划方


        if (user.getId()!=enlisetDTO.getUserId()){
            if (user.getIdentity()!=2){
                throw new CustomizeException(CustomizeErrorCode.ERROR_IDENTITY);
            }
        }
        Integer isFollow = 0;          //0表示未关注,1表示已关注，2表示是本人
        if (user != null) {           //已登录
            isFollow=followService.isFollow(user.getId(),enlisetDTO.getUser().getId());

        }



        model.addAttribute("enlist", enlisetDTO);
        model.addAttribute("isFollow", isFollow);     //未登录时显示未关注


        return "painterBoard";

    }

}
