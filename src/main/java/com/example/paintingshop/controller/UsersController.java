package com.example.paintingshop.controller;


import com.example.paintingshop.dto.ModifyDTO;
import com.example.paintingshop.dto.PaginationDTO;
import com.example.paintingshop.dto.ResultDTO;
import com.example.paintingshop.exception.CustomizeErrorCode;
import com.example.paintingshop.exception.CustomizeException;
import com.example.paintingshop.mapper.FollowMapper;
import com.example.paintingshop.model.*;
import com.example.paintingshop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UsersController {

    @Autowired
    private UserService userService;
    @Autowired
    private DemandService demandService;
    @Autowired
    private PaintingsService paintingsService;
    @Autowired
    private FollowMapper followMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private FollowService followService;

    @GetMapping("/users/{name}/{action}")
    public String users(@PathVariable(name = "name") String name,
                        @PathVariable(name = "action") String action,
                        Model model,
                        HttpServletRequest request) {

        User user1 = (User) request.getSession().getAttribute("user");       //当前操作的用户

        User user = userService.getByName(name);   //拿到访问的用户

        Integer isFollow = 0;          //0表示未关注,1表示已关注，2表示是本人
        if (user1 != null) {           //已登录
            isFollow = followService.isFollow(user1.getId(), user.getId());
            if (isFollow == 2) {
                return "redirect:/profile/demands";
            }
        }


        if ("demands".equals(action)) {
            List<Demand> listByCreator = demandService.getListByCreator(user.getId());
            model.addAttribute("section", "demands");
            model.addAttribute("demands", listByCreator);

        } else if ("paintings".equals(action)) {

            List<Paintings> listByPainterId = paintingsService.getListByPainterId(user.getId());
            model.addAttribute("section", "paintings");
            model.addAttribute("paintings", listByPainterId);
        } else if ("order".equals(action)) {
            model.addAttribute("section", "order");
            model.addAttribute("sectionName", "TA完成的订单");
            PaginationDTO paginationDTO = orderService.listByPainterId(user.getId());
            model.addAttribute("pagination", paginationDTO);
        }

        model.addAttribute("title", name + " 的主页 - 画商店");
        model.addAttribute("user", user);
        model.addAttribute("isFollow", isFollow);     //未登录时显示未关注

        return "users";
    }


    @ResponseBody
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public Object modify(@RequestBody ModifyDTO modifyDTO,
                         HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");    //当前用户
        if (user == null) {
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }
        if (modifyDTO.getContent().equals("")){
            throw new CustomizeException(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }


        if (modifyDTO.getType() == 1) {            //修改备注

            User user1 = new User();
            user1.setId(user.getId());
            user1.setBio(modifyDTO.getContent());

            userService.upDate(user1);

        } else if (modifyDTO.getType() == 2) {       //修改密码

            User user1 = new User();
            user1.setId(user.getId());
            user1.setPassword(modifyDTO.getContent());

            userService.upDate(user1);


        } else if (modifyDTO.getType() == 3) {   //修改支付宝

            User user1 = new User();
            user1.setId(user.getId());
            user1.setAlipayNumber(modifyDTO.getContent());

            userService.upDate(user1);

        } else {
            throw new CustomizeException(CustomizeErrorCode.ERROR_CODE);
        }

        return ResultDTO.okOf();

    }


}
