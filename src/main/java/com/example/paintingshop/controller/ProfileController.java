package com.example.paintingshop.controller;

import com.example.paintingshop.dto.PaginationDTO;
import com.example.paintingshop.exception.CustomizeErrorCode;
import com.example.paintingshop.exception.CustomizeException;
import com.example.paintingshop.model.User;
import com.example.paintingshop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ProfileController {

    @Autowired
    private DemandService demandService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private PaintingsService paintingsService;
    @Autowired
    private EnlistService enlistService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "9") Integer size) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }

        if ("demands".equals(action)) {
            model.addAttribute("section", "demands");
            model.addAttribute("sectionName", "我发布的企划");
            PaginationDTO paginationDTO = demandService.list(user.getId(), page, size);
            model.addAttribute("pagination", paginationDTO);

        } else if ("works".equals(action)) {
            model.addAttribute("section", "works");
            model.addAttribute("sectionName", "我发布的作品");
            PaginationDTO paginationDTO = paintingsService.list(user.getId(), page, size);
            model.addAttribute("pagination", paginationDTO);
        } else if ("enlist".equals(action)) {
            model.addAttribute("section", "enlist");
            model.addAttribute("sectionName", "我应征的企划");
            PaginationDTO paginationDTO = enlistService.list(user.getId(), page, size);
            model.addAttribute("pagination", paginationDTO);

        } else if ("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "我收到的通知");
            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
            model.addAttribute("pagination", paginationDTO);
        } else if ("myOrder".equals(action)) {

            model.addAttribute("section", "myOrder");
            model.addAttribute("sectionName", "我发起的订单");
            PaginationDTO paginationDTO = orderService.listByCustomerId(user.getId(), page, size);
            model.addAttribute("pagination", paginationDTO);
        } else if ("enlistOrder".equals(action)) {
            model.addAttribute("section", "enlistOrder");
            model.addAttribute("sectionName", "我应征的订单");
            PaginationDTO paginationDTO = orderService.listByPainterId(user.getId(), page, size);
            model.addAttribute("pagination", paginationDTO);
        } else if ("follow".equals(action)) {
            model.addAttribute("section", "follow");
            model.addAttribute("sectionName", "我关注的用户");
            PaginationDTO paginationDTO = userService.listByFollow(user.getId(), page, size);
            model.addAttribute("pagination", paginationDTO);
        }

        return "profile";
    }
}
