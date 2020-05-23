package com.example.paintingshop.controller;


import com.example.paintingshop.exception.CustomizeErrorCode;
import com.example.paintingshop.exception.CustomizeException;
import com.example.paintingshop.model.User;
import com.example.paintingshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public String login( HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            throw new CustomizeException(CustomizeErrorCode.REPEAT_LOGIN);
        }
        return "login";
    }

    @PostMapping("/login")
    public String dologin(@RequestParam(value = "username", required = false) String username,
                          @RequestParam(value = "password", required = false) String password,
                          Model model,
                          HttpServletResponse response) {
        model.addAttribute("username", username);
        model.addAttribute("password", password);



        if (username == null || username.equals("")) {
            model.addAttribute("error", "请输入用户名");
            return "login";
        }
        if (password == null || password.equals("")) {
            model.addAttribute("error", "请输入密码");
            return "login";
        }

        User user = new User();
        user.setName(username);
        user.setPassword(password);
        User user1 = userService.login(user);
        if (user1 != null) {
            response.addCookie(new Cookie("token", user1.getToken()));
            return "redirect:/index";
        } else {
            model.addAttribute("error", "用户名或密码不正确");
            return "login";
        }

    }

    //退出登录
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().removeAttribute("user");

        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }

}
