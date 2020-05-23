package com.example.paintingshop.controller;


import com.example.paintingshop.exception.CustomizeErrorCode;
import com.example.paintingshop.exception.CustomizeException;
import com.example.paintingshop.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SettingsController {


    @GetMapping("/settings")
    public String settings(HttpServletRequest request,
                           Model model) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }



        return "settings";
    }
}
