package com.example.paintingshop.controller;


import com.example.paintingshop.dto.OrderCreateDTO;
import com.example.paintingshop.dto.ResultDTO;
import com.example.paintingshop.enums.DemandStateEnum;
import com.example.paintingshop.enums.EnlistStateEnum;
import com.example.paintingshop.enums.OrderStateEnum;
import com.example.paintingshop.exception.CustomizeErrorCode;
import com.example.paintingshop.exception.CustomizeException;
import com.example.paintingshop.mapper.DemandMapper;
import com.example.paintingshop.mapper.EnlistMapper;
import com.example.paintingshop.mapper.OrderMapper;
import com.example.paintingshop.model.*;
import com.example.paintingshop.service.DemandService;
import com.example.paintingshop.service.EnlistService;
import com.example.paintingshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller

public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private DemandService demandService;
    @Autowired
    private EnlistService enlistService;
    @Autowired
    private DemandMapper demandMapper;
    @Autowired
    private EnlistMapper enlistMapper;
    @Autowired
    private OrderMapper orderMapper;


    @ResponseBody
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public Object createOrder(@RequestBody OrderCreateDTO orderCreateDTO,
                              HttpServletRequest request) {                //订单创建


        User user = (User) request.getSession().getAttribute("user");       //当前操作的用户
        if (user==null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }


        Demand demand1 = demandMapper.selectByPrimaryKey(orderCreateDTO.getDemandId());
        if (demand1.getState() != DemandStateEnum.NOT_ACCEPTED.getId()) {
            return ResultDTO.errorOf(CustomizeErrorCode.CHOICE_ENLIST_FAIL_DEMAND);
        }
        Enlist enlist1 = enlistMapper.selectByPrimaryKey(orderCreateDTO.getEnlistId());
        if (enlist1.getState() != EnlistStateEnum.NOT_ACCEPTED.getId()) {
            return ResultDTO.errorOf(CustomizeErrorCode.CHOICE_ENLIST_FAIL_ENLIST);
        }

        OrderExample example = new OrderExample();
        example.createCriteria()
                .andCustomerIdEqualTo(user.getId())
                .andStateEqualTo(OrderStateEnum.TO_BE_PAID.getId());
        List<Order> orders = orderMapper.selectByExample(example);

        if (orders.size()>5){
            return ResultDTO.errorOf(CustomizeErrorCode.TOO_MORE);
        }

        Order order = new Order();
        order.setCustomerId(orderCreateDTO.getCustomerId());
        order.setDemandId(orderCreateDTO.getDemandId());
        order.setPainterId(orderCreateDTO.getPainterId());
        order.setEnlistId(orderCreateDTO.getEnlistId());
        order.setPrice(orderCreateDTO.getPrice());
        order.setState(0);
        String number = Long.toString(orderCreateDTO.getCustomerId()) + Long.toString(orderCreateDTO.getDemandId()) +
                Long.toString(orderCreateDTO.getPainterId()) + System.currentTimeMillis();   //企划方id+企划id+画师id+当前时间=订单编号
        order.setOrderNumber(number);

        orderService.create(order);           //订单创建

        Demand demand = new Demand();
        demand.setId(order.getDemandId());
        demand.setState(DemandStateEnum.ACCEPTED.getId());
        demandService.createOrUpdate(demand);        //修改企划状态为进行中


        Enlist enlist = new Enlist();
        enlist.setId(order.getEnlistId());
        enlist.setState(EnlistStateEnum.ACCEPTED.getId());
        enlistService.update(enlist);           //修改应征状态为已选中

        return ResultDTO.okOf();

    }


    @PostMapping("/orderPaying")
    public String orderPaying(@RequestParam(value = "demandId", required = false) Long demandId,
                              @RequestParam(value = "orderNumber", required = false) String orderNumber,
                              @RequestParam(value = "enlistId", required = false) Long enlistId){             //订单确认收货


        OrderExample example = new OrderExample();
        example.createCriteria()
                .andOrderNumberEqualTo(orderNumber);
        Order upDateOrder = new Order();
        upDateOrder.setState(OrderStateEnum.WAIT_EVALUATE.getId());                //修改订单状态为待评价
        upDateOrder.setGmtModified(System.currentTimeMillis());
        upDateOrder.setGmtPaid(1);      //1表示买家申请支付，2表示卖家申请支付  3表示已操作完成
        orderMapper.updateByExampleSelective(upDateOrder, example);


        Demand demand = new Demand();
        demand.setId(demandId);
        demand.setState(DemandStateEnum.FINISH.getId());
        demandService.createOrUpdate(demand);        //修改企划状态为已经完成


        Enlist enlist = new Enlist();
        enlist.setId(enlistId);
        enlist.setState(EnlistStateEnum.FINISH.getId());
        enlistService.update(enlist);           //修改应征状态为已经完成


        return "redirect:/profile/myOrder";

    }



    @PostMapping("/orderReturn")
    public String orderReturn(@RequestParam(value = "demandId", required = false) Long demandId,
                              @RequestParam(value = "orderNumber", required = false) String orderNumber,
                              @RequestParam(value = "enlistId", required = false) Long enlistId){             //订单退款


        OrderExample example = new OrderExample();
        example.createCriteria()
                .andOrderNumberEqualTo(orderNumber);
        Order upDateOrder = new Order();
        upDateOrder.setState(OrderStateEnum.WAIT_EVALUATE.getId());                //修改订单状态为待评价
        upDateOrder.setGmtModified(System.currentTimeMillis());
        upDateOrder.setGmtPaid(3);      //1表示买家申请支付，2表示卖家申请支付  3表示买家申请退款  4表示已完成
        orderMapper.updateByExampleSelective(upDateOrder, example);


        Demand demand = new Demand();
        demand.setId(demandId);
        demand.setState(DemandStateEnum.FINISH.getId());
        demandService.createOrUpdate(demand);        //修改企划状态为已经完成


        Enlist enlist = new Enlist();
        enlist.setId(enlistId);
        enlist.setState(EnlistStateEnum.FINISH.getId());
        enlistService.update(enlist);           //修改应征状态为已经完成


        return "redirect:/profile/myOrder";

    }




    @PostMapping("/orderPayingPainter")
    public String orderPayingPainter(@RequestParam(value = "demandId", required = false) Long demandId,
                              @RequestParam(value = "orderNumber", required = false) String orderNumber,
                              @RequestParam(value = "enlistId", required = false) Long enlistId){             //订单确认收货


        OrderExample example = new OrderExample();
        example.createCriteria()
                .andOrderNumberEqualTo(orderNumber);
        Order upDateOrder = new Order();
        upDateOrder.setState(OrderStateEnum.WAIT_EVALUATE.getId());                //修改订单状态为待评价
        upDateOrder.setGmtModified(System.currentTimeMillis());
        upDateOrder.setGmtPaid(2);     //1表示买家申请支付，2表示卖家申请支付  3表示买家申请退款  4表示已完成
        orderMapper.updateByExampleSelective(upDateOrder, example);


        Demand demand = new Demand();
        demand.setId(demandId);
        demand.setState(DemandStateEnum.FINISH.getId());
        demandService.createOrUpdate(demand);        //修改企划状态为已经完成


        Enlist enlist = new Enlist();
        enlist.setId(enlistId);
        enlist.setState(EnlistStateEnum.FINISH.getId());
        enlistService.update(enlist);           //修改应征状态为已经完成


        return "redirect:/profile/myOrder";

    }




}
