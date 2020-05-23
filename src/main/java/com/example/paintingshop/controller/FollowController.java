package com.example.paintingshop.controller;


import com.example.paintingshop.dto.DemandCreateDTO;
import com.example.paintingshop.dto.FollowDTO;
import com.example.paintingshop.dto.ResultDTO;
import com.example.paintingshop.exception.CustomizeErrorCode;
import com.example.paintingshop.mapper.FollowMapper;
import com.example.paintingshop.model.Follow;
import com.example.paintingshop.model.FollowExample;
import com.example.paintingshop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class FollowController {

    @Autowired
    private FollowMapper followMapper;


    @ResponseBody
    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    public Object follow(@RequestBody FollowDTO followDTO,
                         HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);   //未登录
        }


        Follow follow = new Follow();
        follow.setHeroineId(followDTO.getHeroineId());
        follow.setFollowerId(user.getId());
        follow.setGmtCreate(System.currentTimeMillis());
        follow.setGmtModified(System.currentTimeMillis());

        followMapper.insert(follow);            //创建关注表


        return ResultDTO.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/unFollow", method = RequestMethod.POST)
    public Object unFollow(@RequestBody FollowDTO followDTO,
                         HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);   //未登录
        }


        FollowExample example = new FollowExample();
        example.createCriteria()
                .andHeroineIdEqualTo(followDTO.getHeroineId())
                .andFollowerIdEqualTo(user.getId());
        List<Follow> followList = followMapper.selectByExample(example);   //拿到数据库里的关注数据
        if (followList.size()==0){
            return ResultDTO.errorOf(CustomizeErrorCode.FOLLOW_NOT_FOUND);    //找不到关注数据
        }

        followMapper.deleteByPrimaryKey(followList.get(0).getId());   //删除此关注数据

        return ResultDTO.okOf();
    }

}
