package com.example.paintingshop.controller;


import com.example.paintingshop.dto.EnlistCreateDTO;
import com.example.paintingshop.dto.ResultDTO;
import com.example.paintingshop.enums.DemandStateEnum;
import com.example.paintingshop.enums.EnlistStateEnum;
import com.example.paintingshop.enums.UserTypeEnum;
import com.example.paintingshop.exception.CustomizeErrorCode;
import com.example.paintingshop.mapper.EnlistMapper;
import com.example.paintingshop.model.Demand;
import com.example.paintingshop.model.Enlist;
import com.example.paintingshop.model.EnlistExample;
import com.example.paintingshop.model.User;
import com.example.paintingshop.service.DemandService;
import com.example.paintingshop.service.EnlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class EnlistController {

    @Autowired
    private EnlistService enlistService;
    @Autowired
    private EnlistMapper enlistMapper;
    @Autowired
    private DemandService demandService;

    @ResponseBody
    @RequestMapping(value =  "/enlist", method = RequestMethod.POST)
    public Object post(@RequestBody EnlistCreateDTO enlistCreateDTO,
                       HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);    //未登录
        }
        if (user.getIdentity()!= UserTypeEnum.PAINTER.getId()) {
            return ResultDTO.errorOf(CustomizeErrorCode.USER_NOT_PAINTER);      //不是画师
        }

        //判断是否已被此人接受
        EnlistExample example = new EnlistExample();
        example.createCriteria()
                .andDemandIdEqualTo(enlistCreateDTO.getDemandId())
                .andUserIdEqualTo(user.getId());
        List<Enlist> enlists = enlistMapper.selectByExample(example);
        if (enlists.size() != 0) {
            if (enlists.get(0).getState()==EnlistStateEnum.GIVE_UP.getId()) {
                return ResultDTO.errorOf(CustomizeErrorCode.GIVE_UP_ENLIST);   //放弃的不可再次接受
            } else {
                return ResultDTO.errorOf(CustomizeErrorCode.REPEAT_ENLIST);   //不可重复接受
            }
        } else {
            Enlist enlist = new Enlist();
            enlist.setDemandId(enlistCreateDTO.getDemandId());
            enlist.setUserId(user.getId());
            enlist.setPrice(enlistCreateDTO.getPrice());
            enlist.setClosingDate(enlistCreateDTO.getClosingDate());
            enlist.setContent(enlistCreateDTO.getContent());
            enlist.setState(EnlistStateEnum.NOT_ACCEPTED.getId());
            enlist.setGmtCreate(System.currentTimeMillis());
            enlist.setGmtModified(enlist.getGmtCreate());

            enlistService.insert(enlist);    //写入应征表

            demandService.incEnlist(enlistCreateDTO.getDemandId());   //累加应征数


            return ResultDTO.okOf();
        }

    }



    @PostMapping("/enlistGiveUp")
    public String enlistGiveUp( @RequestParam(value = "enlistId", required = false) Long enlistId,
                              Model model) {

        Enlist enlist = new Enlist();
        enlist.setId(enlistId);
        enlist.setState(EnlistStateEnum.GIVE_UP.getId());              //应征状态修改为已经放弃
        enlistService.update(enlist);

        Enlist enlist1 = enlistMapper.selectByPrimaryKey(enlistId);

        demandService.reduceEnlist(enlist1.getDemandId());   //减少应征数




        return "redirect:/profile/enlist";
    }

}
