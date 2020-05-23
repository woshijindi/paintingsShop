package com.example.paintingshop.controller;


import com.example.paintingshop.dto.DemandDTO;
import com.example.paintingshop.dto.EnlistDTO;
import com.example.paintingshop.exception.CustomizeErrorCode;
import com.example.paintingshop.exception.CustomizeException;
import com.example.paintingshop.model.User;
import com.example.paintingshop.service.DemandService;
import com.example.paintingshop.service.EnlistService;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class BoardController {

    @Autowired
    private DemandService demandService;
    @Autowired
    private EnlistService enlistService;

    @GetMapping("/board/{id}")
    public String board(@PathVariable(name = "id") Long id,
                        Model model,
                        HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }

        DemandDTO demandDTO = demandService.getById(id);      //得到当前需求

        if (user.getId()!=demandDTO.getCreator()){
            if (user.getIdentity()!=2){
                throw new CustomizeException(CustomizeErrorCode.ERROR_IDENTITY);
            }
        }

        List<EnlistDTO> enlists = enlistService.listBytargetId(id);  //得到应征者列表






        model.addAttribute("demand", demandDTO);
        model.addAttribute("enlists",enlists);

        return "board";
    }


}
