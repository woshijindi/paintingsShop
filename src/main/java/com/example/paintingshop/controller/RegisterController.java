package com.example.paintingshop.controller;

import com.example.paintingshop.enums.UserTypeEnum;
import com.example.paintingshop.mapper.UserMapper;
import com.example.paintingshop.model.User;
import com.example.paintingshop.model.UserExample;
import com.example.paintingshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String doregister(@RequestParam(value = "username", required = false) String username,
                             @RequestParam(value = "password", required = false) String password,
                             Model model,
                             HttpServletResponse response) {
        model.addAttribute("username", username);
        model.addAttribute("password", password);

        if (username == null || username.equals("")) {
            model.addAttribute("error", "名字不能为空");
            return "register";
        }
        if (password == null || password.equals("")) {
            model.addAttribute("error", "密码不能为空");
            return "register";
        }

        User user = new User();
        user.setName(username);
        user.setPassword(password);
        user.setBio("还没有个人描述");
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        user.setIdentity(UserTypeEnum.MEMBER.getId());
        User user1 = userService.create(user);
        if (user1 != null) {
            response.addCookie(new Cookie("token", user1.getToken()));
            return "redirect:/index";
        } else {
            model.addAttribute("error", "用户名已存在，请更换~");
            return "register";
        }

    }
}
