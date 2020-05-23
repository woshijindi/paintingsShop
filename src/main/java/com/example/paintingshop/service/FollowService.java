package com.example.paintingshop.service;

import com.example.paintingshop.mapper.FollowMapper;
import com.example.paintingshop.mapper.UserMapper;
import com.example.paintingshop.model.Follow;
import com.example.paintingshop.model.FollowExample;
import com.example.paintingshop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {

    @Autowired
    private FollowMapper followMapper;


    public Integer isFollow(Long userId, Long toUserId) {
        Integer isFollow = 0;         //0表示未关注,1表示已关注，2表示是本人

        if (userId == toUserId) {
            isFollow=2;           //是本人
            return isFollow;
        } else {
            FollowExample example = new FollowExample();
            example.createCriteria()
                    .andHeroineIdEqualTo(toUserId)
                    .andFollowerIdEqualTo(userId);
            List<Follow> followList = followMapper.selectByExample(example);
            if (followList.size() != 0) {            //在关注表中可以查找到，显示已关注
                isFollow = 1;
            }
            return isFollow;
        }

    }

}
