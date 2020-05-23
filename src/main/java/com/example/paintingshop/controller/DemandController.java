package com.example.paintingshop.controller;


import com.example.paintingshop.dto.DemandDTO;
import com.example.paintingshop.dto.EnlistDTO;
import com.example.paintingshop.enums.DemandStateEnum;
import com.example.paintingshop.mapper.DemandMapper;
import com.example.paintingshop.model.Demand;
import com.example.paintingshop.model.Enlist;
import com.example.paintingshop.model.User;
import com.example.paintingshop.service.DemandService;
import com.example.paintingshop.service.EnlistService;
import com.example.paintingshop.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class DemandController {

    @Autowired
    private DemandService demandService;
    @Autowired
    private EnlistService enlistService;
    @Autowired
    private FollowService followService;


    @GetMapping("/demand/{id}")
    public String demand(@PathVariable(name = "id") Long id,
                         Model model,
                         HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");       //当前操作的用户


        DemandDTO demandDTO = demandService.getById(id);      //得到当前需求
        List<EnlistDTO> enlists = enlistService.listBytargetId(id);  //得到应征者列表

        Integer isFollow = 0;          //0表示未关注,1表示已关注，2表示是本人
        if (user != null) {           //已登录
            isFollow=followService.isFollow(user.getId(),demandDTO.getCreator());
        }


        model.addAttribute("demand", demandDTO);
        model.addAttribute("enlists",enlists);
        model.addAttribute("isFollow", isFollow);     //未登录时显示未关注


        return "demand";
    }

    @PostMapping("/demandStop")
    public String demandStop( @RequestParam(value = "demandId", required = false) Long demandId,
                         Model model) {

        Demand demand = new Demand();
        demand.setId(demandId);
        demand.setState(DemandStateEnum.END.getId());              //需求状态修改为手动终止
        demandService.createOrUpdate(demand);


        return "redirect:/profile/demands";
    }





}
