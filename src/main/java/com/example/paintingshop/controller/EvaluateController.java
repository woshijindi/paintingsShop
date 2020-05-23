package com.example.paintingshop.controller;


import com.example.paintingshop.dto.DemandCreateDTO;
import com.example.paintingshop.dto.EvaluateDTO;
import com.example.paintingshop.dto.ResultDTO;
import com.example.paintingshop.enums.DemandStateEnum;
import com.example.paintingshop.enums.EnlistStateEnum;
import com.example.paintingshop.enums.OrderStateEnum;
import com.example.paintingshop.exception.CustomizeErrorCode;
import com.example.paintingshop.mapper.EvaluateMapper;
import com.example.paintingshop.mapper.OrderMapper;
import com.example.paintingshop.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class EvaluateController {

    @Autowired
    private EvaluateMapper evaluateMapper;
    @Autowired
    private OrderMapper orderMapper;


    @ResponseBody
    @RequestMapping(value = "/evaluate", method = RequestMethod.POST)
    public Object evaluate(@RequestBody EvaluateDTO evaluateDTO,
                           HttpServletRequest request,
                           Model model){


        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);   //未登录
        }

        Evaluate evaluate = new Evaluate();
        evaluate.setEvaluator(user.getId());
        evaluate.setReceiver(evaluateDTO.getReceiver());
        evaluate.setReceiverType(evaluateDTO.getReceiverType());
        evaluate.setDemandId(evaluateDTO.getDemandId());
        evaluate.setOrderId(evaluateDTO.getOrderId());
        evaluate.setEnlistId(evaluateDTO.getEnlistId());
        evaluate.setContent(evaluateDTO.getContent());
        evaluate.setGmtCreate(System.currentTimeMillis());

        evaluateMapper.insert(evaluate);    //存入评价表


        OrderExample example = new OrderExample();
        example.createCriteria()
                .andIdEqualTo(evaluateDTO.getOrderId());
        Order upDateOrder = new Order();
        upDateOrder.setState(OrderStateEnum.COMPLETED.getId());                //修改订单状态为已完成
        upDateOrder.setGmtModified(System.currentTimeMillis());
        orderMapper.updateByExampleSelective(upDateOrder, example);



        return ResultDTO.okOf();

    }

}
