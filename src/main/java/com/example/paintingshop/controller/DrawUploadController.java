package com.example.paintingshop.controller;


import com.example.paintingshop.enums.*;
import com.example.paintingshop.exception.CustomizeErrorCode;
import com.example.paintingshop.exception.CustomizeException;
import com.example.paintingshop.mapper.DemandMapper;
import com.example.paintingshop.mapper.OrderMapper;
import com.example.paintingshop.model.*;
import com.example.paintingshop.provider.UCloudProvider;
import com.example.paintingshop.service.DemandService;
import com.example.paintingshop.service.NotificationService;
import com.example.paintingshop.service.PaintingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class DrawUploadController {

    @Autowired
    private UCloudProvider uCloudProvider;
    @Autowired
    private PaintingsService paintingsService;
    @Autowired
    private DemandService demandService;
    @Autowired
    private DemandMapper demandMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private NotificationService notificationService;



    @RequestMapping("/drawUpload")
    public String drawUpload() {
        return "drawUpload";
    }


    @PostMapping("/drawUpload")
    public String doPublish(@RequestParam(value = "paintingsType", required = false) Integer paintingsType,
                            @RequestParam(value = "paintingsStyle", required = false) Integer paintingsStyle,
                            @RequestParam(value = "paintingsMethod", required = false) Integer paintingsMethod,
                            HttpServletRequest request) {


        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }


        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");    //拿到文件
        try {
            String fileName = uCloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
            Paintings paintings = new Paintings();
            paintings.setPainterId(user.getId());
            paintings.setPaintingsUrl(fileName);
            paintings.setFollowCount(0);
            paintings.setType(PaintingsTypeEnum.getValueByCode(paintingsType));
            paintings.setStyle(PaintingsStyleEnum.getValueByCode(paintingsStyle));
            paintings.setMethod(PaintingsMethodEnum.getValueByCode(paintingsMethod));

            paintingsService.insert(paintings);     //创建

        } catch (IOException e) {
            e.printStackTrace();
        }


        return "redirect:/profile/works";
    }


    @PostMapping("/drawSend")               //发货管理
    public String drawSend(@RequestParam(value = "demandId", required = false) Long demandId,
                           @RequestParam(value = "enlistId", required = false) Long enlistId,
                           HttpServletRequest request,
                           Model model) {


        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "drawUpload";
        }


        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");    //拿到文件
        try {
            String fileName = uCloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());


            Demand demand = demandMapper.selectByPrimaryKey(demandId);
            demand.setPaintingsUrl(fileName);
            demandService.createOrUpdate(demand);      //将画稿存入需求表，代表已发货

            Demand demand1 = demandMapper.selectByPrimaryKey(demandId);
            if (demand1.getPaintingsUrl() == null) {
                throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);       //失败
            } else {

                Order upDateOrder = new Order();
                upDateOrder.setState(OrderStateEnum.TO_BE_HARVESTED.getId());

                OrderExample example = new OrderExample();
                example.createCriteria()
                        .andEnlistIdEqualTo(enlistId);
                orderMapper.updateByExampleSelective(upDateOrder, example);             //修改订单状态为待收货

                Notification notification = new Notification();
                notification.setNotifier(user.getId());                    //通知者是画师
                notification.setReceiver(demand.getCreator());
                notification.setOuterId(demandId);
                notification.setGmtCreate(System.currentTimeMillis());
                notification.setType(NotificationTypeEnum.DRAW_SEND.getId());
                notification.setState(NotificationStateEnum.NOT_VIEW.getId());
                notificationService.create(notification);                      //写入通知表


            }


        } catch (IOException e) {
            e.printStackTrace();
        }


        return "redirect:/profile/enlist";
    }

}
