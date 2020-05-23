package com.example.paintingshop.controller;


import com.example.paintingshop.exception.CustomizeErrorCode;
import com.example.paintingshop.exception.CustomizeException;
import com.example.paintingshop.mapper.UserMapper;
import com.example.paintingshop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class WebSocketController {

    @Autowired
    private UserMapper userMapper;

//    @GetMapping("/webSocketPublic")
//    public String webSocketPublic(HttpServletRequest request,
//                                  Model model) {
//
//        User user = (User) request.getSession().getAttribute("user");       //当前操作的用户
//        if (user == null) {
//            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
//        }
//
//        model.addAttribute("user", user);
//        return "webSocketPublic";
//    }

    @GetMapping("/webSocketPrivate/{toUserId}")
    public String webSocketPublic(HttpServletRequest request,
                                  @PathVariable("toUserId") Long toUserId,
                                  Model model) {

        User user = (User) request.getSession().getAttribute("user");       //当前操作的用户
        User toUser = userMapper.selectByPrimaryKey(toUserId);                    //聊天对象
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
