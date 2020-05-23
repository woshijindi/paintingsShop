package com.example.paintingshop.controller;


import com.example.paintingshop.dto.PaginationDTO;
import com.example.paintingshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PainterSquareController {

    @Autowired
    private UserService userService;

    @GetMapping("/painterSquare")
    public String painter(Model model,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size){


        PaginationDTO paginationUser = userService.list(page,size);          //获得画师列表
        model.addAttribute("pagination",paginationUser);

        return "painterSquare";
    }
}
