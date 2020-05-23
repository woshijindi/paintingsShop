package com.example.paintingshop.controller;


import com.example.paintingshop.dto.DemandCreateDTO;
import com.example.paintingshop.dto.ResultDTO;
import com.example.paintingshop.enums.DemandPriceEnum;
import com.example.paintingshop.enums.DemandPurposeEnum;
import com.example.paintingshop.enums.DemandSpecificationsEnum;
import com.example.paintingshop.enums.DemandStateEnum;
import com.example.paintingshop.exception.CustomizeErrorCode;
import com.example.paintingshop.model.Demand;
import com.example.paintingshop.model.User;
import com.example.paintingshop.service.DemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private DemandService demandService;


    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }


    @ResponseBody
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public Object doPublish(@RequestBody DemandCreateDTO demandCreateDTO,
                            HttpServletRequest request,
                            Model model) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);   //未登录
        }

        if (demandCreateDTO.getTitle() == null || demandCreateDTO.getTitle().equals("")) {

            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);   //必须全部输入

        }
        if (demandCreateDTO.getPrice() == null || demandCreateDTO.getPrice() == 0) {

            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);   //必须全部输入

        }
        if (demandCreateDTO.getClosingDate() == null || demandCreateDTO.getClosingDate().equals("")) {

            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);   //必须全部输入

        }
        if (demandCreateDTO.getDescription() == null || demandCreateDTO.getDescription().equals("")) {

            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);   //必须全部输入

        }
        if (demandCreateDTO.getSpecifications() == null) {

            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);   //必须全部输入

        }
        if (demandCreateDTO.getPurpose() == null) {

            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);   //必须全部输入

        }

        Demand demand = new Demand();
        demand.setTitle(demandCreateDTO.getTitle());
        demand.setPrice(DemandPriceEnum.getValueByCode(demandCreateDTO.getPrice()));
        demand.setClosingDate(demandCreateDTO.getClosingDate());
        demand.setDescription(demandCreateDTO.getDescription());
        demand.setSpecifications(DemandSpecificationsEnum.getValueByCode(demandCreateDTO.getSpecifications()));
        demand.setCreator(user.getId());
        demand.setState(DemandStateEnum.NOT_ACCEPTED.getId());
        demand.setFollowCount(0);
        demand.setPurpose(DemandPurposeEnum.getValueByCode(demandCreateDTO.getPurpose()));
        demand.setEnlistCount(0);

        demandService.createOrUpdate(demand);

        return ResultDTO.okOf();
    }

}
