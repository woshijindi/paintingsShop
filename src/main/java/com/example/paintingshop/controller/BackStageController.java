package com.example.paintingshop.controller;


import com.example.paintingshop.dto.ApplyDTO;
import com.example.paintingshop.dto.DemandCreateDTO;
import com.example.paintingshop.dto.PaginationDTO;
import com.example.paintingshop.dto.ResultDTO;
import com.example.paintingshop.enums.ApplyStateEnum;
import com.example.paintingshop.enums.NotificationStateEnum;
import com.example.paintingshop.enums.NotificationTypeEnum;
import com.example.paintingshop.exception.CustomizeErrorCode;
import com.example.paintingshop.exception.CustomizeException;
import com.example.paintingshop.mapper.ApplyMapper;
import com.example.paintingshop.mapper.OrderMapper;
import com.example.paintingshop.model.*;
import com.example.paintingshop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller

public class BackStageController {

    @Autowired
    private UserService userService;
    @Autowired
    private DemandService demandService;
    @Autowired
    private PaintingsService paintingsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ApplyMapper applyMapper;
    @Autowired
    private NotificationService notificationService;


    @GetMapping("/backStage/{action}")
    public String backStage(@PathVariable(name = "action") String action,
                            Model model,
                            HttpServletRequest request,
                            @RequestParam(name = "page", defaultValue = "1") Integer page,
                            @RequestParam(name = "size", defaultValue = "9") Integer size) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        } else if (user.getIdentity() != 2) {
            throw new CustomizeException(CustomizeErrorCode.ERROR_IDENTITY);
        }

        if ("usersController".equals(action)) {
            model.addAttribute("section", "usersController");
            model.addAttribute("sectionName", "用户管理");
            PaginationDTO paginationDTO = userService.listAll(page, size);
            model.addAttribute("pagination", paginationDTO);

        } else if ("demandsController".equals(action)) {
            model.addAttribute("section", "demandsController");
            model.addAttribute("sectionName", "企划管理");
            PaginationDTO paginationDTO = demandService.listAll(page, size);
            model.addAttribute("pagination", paginationDTO);
        } else if ("worksController".equals(action)) {
            model.addAttribute("section", "worksController");
            model.addAttribute("sectionName", "作品管理");
            PaginationDTO paginationDTO = paintingsService.list(page, size);
            model.addAttribute("pagination", paginationDTO);

        } else if ("ordersController".equals(action)) {
            model.addAttribute("section", "ordersController");
            model.addAttribute("sectionName", "订单管理");
            PaginationDTO paginationDTO = orderService.listAll(page, size);
            model.addAttribute("pagination", paginationDTO);
        }else if ("painterApply".equals(action)) {
            model.addAttribute("section", "painterApply");
            model.addAttribute("sectionName", "画师申请");
            PaginationDTO paginationDTO = userService.listByApply(page, size);
            model.addAttribute("pagination", paginationDTO);
        } else if ("payApply".equals(action)) {
            model.addAttribute("section", "payApply");
            model.addAttribute("sectionName", "模拟发货");
            PaginationDTO paginationDTO = orderService.listAllApply(page, size);
            model.addAttribute("pagination", paginationDTO);
        }


        return "backStage";
    }




    @PostMapping("/backStagePay")
    public String backStagePay(@RequestParam(value = "orderNumber", required = false) String orderNumber){             //订单确认收货


        OrderExample example = new OrderExample();
        example.createCriteria()
                .andOrderNumberEqualTo(orderNumber);
        Order upDateOrder = new Order();
        upDateOrder.setGmtPaid(4);        //1表示买家申请支付，2表示卖家申请支付  3表示买家申请退款  4表示已完成
        orderMapper.updateByExampleSelective(upDateOrder, example);


        return "redirect:/backStage/payApply";

    }




    @ResponseBody
    @RequestMapping(value = "/examine", method = RequestMethod.POST)
    public Object doExamine(@RequestBody ApplyDTO applyDTO,
                            HttpServletRequest request,
                            Model model) {

        User user1 = (User) request.getSession().getAttribute("user");

        if (applyDTO.getResult()==1){              //审核通过

            User user = new User();
            user.setId(applyDTO.getUserId());
            user.setIdentity(1);
            userService.upDate(user);  //修改为画师

            ApplyExample example = new ApplyExample();
            example.createCriteria()
                    .andUserIdEqualTo(applyDTO.getUserId());
            applyMapper.deleteByExample(example);    //删除申请记录

            Notification notification = new Notification();
            notification.setNotifier(user1.getId());
            notification.setReceiver(applyDTO.getUserId());
            notification.setOuterId(user1.getId());
            notification.setGmtCreate(System.currentTimeMillis());
            notification.setType(NotificationTypeEnum.SUCCESS.getId());
            notification.setState(NotificationStateEnum.NOT_VIEW.getId());
            notificationService.create(notification);   //写入通知表


        }else if (applyDTO.getResult()==2){


            Apply apply = new Apply();
            apply.setState(ApplyStateEnum.FAIL.getId());

            ApplyExample example = new ApplyExample();
            example.createCriteria()
                    .andUserIdEqualTo(applyDTO.getUserId());
            applyMapper.updateByExampleSelective(apply,example);  //修改状态

            Notification notification = new Notification();
            notification.setNotifier(user1.getId());
            notification.setReceiver(applyDTO.getUserId());
            notification.setOuterId(user1.getId());
            notification.setGmtCreate(System.currentTimeMillis());
            notification.setType(NotificationTypeEnum.NO_EXAMINE.getId());
            notification.setState(NotificationStateEnum.NOT_VIEW.getId());
            notificationService.create(notification);   //写入通知表



        }else {
            return ResultDTO.errorOf(CustomizeErrorCode.ERROR_CODE);
        }


        return ResultDTO.okOf();


    }



}
