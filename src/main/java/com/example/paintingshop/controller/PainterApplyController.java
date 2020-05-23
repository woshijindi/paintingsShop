package com.example.paintingshop.controller;


import com.example.paintingshop.dto.ResultDTO;
import com.example.paintingshop.enums.ApplyStateEnum;
import com.example.paintingshop.exception.CustomizeErrorCode;
import com.example.paintingshop.exception.CustomizeException;
import com.example.paintingshop.mapper.ApplyMapper;
import com.example.paintingshop.mapper.PaintingsMapper;
import com.example.paintingshop.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PainterApplyController {
    @Autowired
    private ApplyMapper applyMapper;
    @Autowired
    private PaintingsMapper paintingsMapper;


    @ResponseBody
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public Object painterApply(HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            throw (new CustomizeException(CustomizeErrorCode.NO_LOGIN));
        }


        PaintingsExample example2 = new PaintingsExample();
        example2.createCriteria()
                .andPainterIdEqualTo(user.getId());
        List<Paintings> paintingsList = paintingsMapper.selectByExample(example2);
        if (paintingsList.size()<3){
            throw new CustomizeException(CustomizeErrorCode.NO_QUALIFICATION);
        }
        if (user.getAlipayNumber()==null){
            throw new CustomizeException(CustomizeErrorCode.NO_QUALIFICATION);
        }




        ApplyExample example = new ApplyExample();
        example.createCriteria()
                .andUserIdEqualTo(user.getId());
        List<Apply> applyList = applyMapper.selectByExample(example);


        if (applyList.size() == 0) {                          //没有待审核记录，创建审核申请
            Apply apply = new Apply();
            apply.setUserId(user.getId());
            apply.setState(ApplyStateEnum.PENDING_REVIEW.getId());
            apply.setGmtCreate(System.currentTimeMillis());
            apply.setGmtModified(apply.getGmtCreate());
            applyMapper.insert(apply);
            return ResultDTO.okOf();
        } else {
            Apply apply1 = applyList.get(0);
            if (apply1.getState() == 0) {                     //审核中，请勿重复提交
                return ResultDTO.errorOf(CustomizeErrorCode.REPEAT_APPLY);
            } else if (apply1.getState() == 1) {                    //上次申请被驳回，再次申请成为画师
                Apply updateApply = new Apply();
                updateApply.setState(ApplyStateEnum.PENDING_REVIEW.getId());
                updateApply.setGmtModified(System.currentTimeMillis());


                ApplyExample example1 = new ApplyExample();
                example.createCriteria()
                        .andIdEqualTo(apply1.getId());
                applyMapper.updateByExampleSelective(updateApply, example1);
                return ResultDTO.okOf();
            } else {
                throw new CustomizeException(CustomizeErrorCode.SYS_ERROR);
            }

        }

    }

}
