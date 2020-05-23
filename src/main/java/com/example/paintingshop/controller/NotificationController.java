package com.example.paintingshop.controller;


import com.example.paintingshop.dto.*;
import com.example.paintingshop.enums.NotificationStateEnum;
import com.example.paintingshop.enums.NotificationTypeEnum;
import com.example.paintingshop.exception.CustomizeErrorCode;
import com.example.paintingshop.exception.CustomizeException;
import com.example.paintingshop.mapper.DemandMapper;
import com.example.paintingshop.mapper.NotificationMapper;
import com.example.paintingshop.mapper.UserMapper;
import com.example.paintingshop.model.Notification;
import com.example.paintingshop.model.User;
import com.example.paintingshop.service.DemandService;
import com.example.paintingshop.service.EnlistService;
import com.example.paintingshop.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class NotificationController {
    @Autowired
    private DemandService demandService;
    @Autowired
    private EnlistService enlistService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String demand(@PathVariable(name = "id") Long notificationId,
                         HttpServletRequest request,
                         Model model) {

        Notification notification = notificationMapper.selectByPrimaryKey(notificationId);
        notification.setState(NotificationStateEnum.VIEW.getId());    //已读
        notificationService.update(notification);

        if (notification.getType()==0 ||notification.getType()==1||notification.getType()==2) {
            Long id = notification.getOuterId();   //需求id
            DemandDTO demandDTO = demandService.getById(id);      //得到当前需求
            List<EnlistDTO> enlists = enlistService.listBytargetId(id);  //得到应征者列表


            model.addAttribute("demand", demandDTO);
            model.addAttribute("enlists", enlists);

            return "demand";
        } else {

            User user = (User) request.getSession().getAttribute("user");       //当前操作的用户
            User toUser = userMapper.selectByPrimaryKey(notification.getNotifier());                    //聊天对象
            if (user == null) {
                throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
            }
            if (toUser == null) {
                throw new CustomizeException(CustomizeErrorCode.USER_NOT_FOUND);
            }


            model.addAttribute("user", user);
            model.addAttribute("toUser",toUser);
            return "webSocketPrivate";

        }



    }


    @ResponseBody
    @RequestMapping(value = "/invitation", method = RequestMethod.POST)
    public Object invitation(@RequestBody InvitationDTO invitationDTO,
                             HttpServletRequest request) {


        Notification notification = new Notification();        //创建通知信息
        notification.setNotifier(invitationDTO.getNotifier());
        notification.setReceiver(invitationDTO.getReceiver());
        notification.setType(NotificationTypeEnum.CHAT_INVITATION.getId());
        notification.setState(NotificationStateEnum.NOT_VIEW.getId());
        notification.setOuterId(invitationDTO.getNotifier());
        notification.setGmtCreate(System.currentTimeMillis());


        int i = notificationService.create(notification);//写入通知表
        if (i==1){       //重复通知
            return ResultDTO.errorOf(CustomizeErrorCode.REPEAT_NOTIFICATION);
        }

        return ResultDTO.okOf();


    }
}


