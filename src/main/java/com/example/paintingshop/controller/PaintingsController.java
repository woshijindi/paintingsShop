package com.example.paintingshop.controller;


import com.example.paintingshop.mapper.PaintingsMapper;
import com.example.paintingshop.mapper.UserMapper;
import com.example.paintingshop.model.Paintings;
import com.example.paintingshop.model.User;
import com.example.paintingshop.service.FollowService;
import com.example.paintingshop.service.PaintingsService;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller

public class PaintingsController {

    @Autowired
    private PaintingsMapper paintingsMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FollowService followService;


    @GetMapping("/paintings/{id}")
    public String paintings(@PathVariable(name = "id") Long id,
                            Model model,
                            HttpServletRequest request) {

        User user1 = (User) request.getSession().getAttribute("user");       //当前操作的用户


        Paintings paintings = paintingsMapper.selectByPrimaryKey(id);                    //作品
        User user = userMapper.selectByPrimaryKey(paintings.getPainterId());                  //作者

        Integer isFollow = 0;          //0表示未关注,1表示已关注，2表示是本人
        if (user1 != null) {           //已登录
            isFollow=followService.isFollow(user1.getId(),user.getId());
        }


        model.addAttribute("paintings",paintings);
        model.addAttribute("user",user);
        model.addAttribute("isFollow", isFollow);     //未登录时显示未关注


        return "paintings";

    }




    @PostMapping("/paintingsDelete")
    public String paintingsDelete(@RequestParam(value = "id", required = false) Long id){

        paintingsMapper.deleteByPrimaryKey(id);

        return "redirect:/profile/works";

    }


}
