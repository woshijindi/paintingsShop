package com.example.paintingshop.controller;


import com.example.paintingshop.dto.PaginationDTO;
import com.example.paintingshop.model.User;
import com.example.paintingshop.service.DemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private DemandService demandService;

    @GetMapping("/")
    public String index1(HttpServletRequest request,
                         Model model,
                         @RequestParam(name = "page", defaultValue = "1") Integer page,
                         @RequestParam(name = "size", defaultValue = "5") Integer size){

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "index";
        } else {
            model.addAttribute("section", "demands");
            model.addAttribute("sectionName", "我发布的企划");
            PaginationDTO paginationDTO = demandService.list(user.getId(), page, size);
            model.addAttribute("pagination",paginationDTO);
            return "profile";
        }
    }

    @GetMapping("/index")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size) {


        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "index";
        } else {
            model.addAttribute("section", "demands");
            model.addAttribute("sectionName", "我发布的企划");
            PaginationDTO paginationDTO = demandService.list(user.getId(), page, size);
            model.addAttribute("pagination",paginationDTO);
            return "profile";
        }

    }
}
