package com.example.paintingshop.service;


import com.example.paintingshop.dto.PaginationDTO;
import com.example.paintingshop.dto.UserDTO;
import com.example.paintingshop.enums.ApplyStateEnum;
import com.example.paintingshop.enums.UserTypeEnum;
import com.example.paintingshop.exception.CustomizeErrorCode;
import com.example.paintingshop.exception.CustomizeException;
import com.example.paintingshop.mapper.ApplyMapper;
import com.example.paintingshop.mapper.FollowMapper;
import com.example.paintingshop.mapper.PaintingsMapper;
import com.example.paintingshop.mapper.UserMapper;
import com.example.paintingshop.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PaintingsMapper paintingsMapper;
    @Autowired
    private FollowMapper followMapper;
    @Autowired
    private ApplyMapper applyMapper;


    public PaginationDTO listByFollow(Long id, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();


        FollowExample example3 = new FollowExample();
        example3.createCriteria()
                .andFollowerIdEqualTo(id);
        List<Follow> followList = followMapper.selectByExample(example3);        //得到我的关注列表

        if (followList.size() == 0) {
            return new PaginationDTO();
        }

        Set<Long> heroineList = followList.stream().map(follow -> follow.getHeroineId()).collect(Collectors.toSet());//得到所有被关注者ID
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(heroineList);

        UserExample example = new UserExample();
        example.createCriteria()
                .andIdIn(userIds);                  //所有user

        Integer totalCount = (int) userMapper.countByExample(example);
        Integer totalPage; //总页数

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        if (page < 1) {
            page = 1;
        }

        paginationDTO.setPagination(totalPage, page);

        Integer offset = size * (page - 1);   //从何处开始呈现
        example.setOrderByClause("gmt_create desc");
        List<User> userList1 = userMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));   //被关注的user表


        List<UserDTO> userList = new ArrayList<>();
        for (User user : userList1) {
            UserDTO userDTO = new UserDTO();

            PaintingsExample example2 = new PaintingsExample();
            example2.createCriteria()
                    .andPainterIdEqualTo(user.getId());
            example2.setOrderByClause("gmt_create desc");
            List<Paintings> paintingsList = paintingsMapper.selectByExampleWithRowbounds(example2, new RowBounds(0, 3));  //得到画师作品3张

            BeanUtils.copyProperties(user, userDTO);
            userDTO.setPaintingsList(paintingsList);
            userList.add(userDTO);
        }
        paginationDTO.setUserDTOs(userList);
        return paginationDTO;

    }


    public User create(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andNameEqualTo(user.getName());
        List<User> users = userMapper.selectByExample(userExample);

        if (users.size() == 0) {
            //创建
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAccountId(user.getGmtCreate());
            user.setAvatarUrl("https://dss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1361981880,3052617388&fm=111&gp=0.jpg");
            userMapper.insert(user);
            return user;
        } else {
            //用户名已存在
            return null;
        }
    }
    //更新
    public void upDate(User user){

            User updateUser = new User();
            updateUser.setName(user.getName());
            updateUser.setPassword(user.getPassword());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setAge(user.getAge());
            updateUser.setBio(user.getBio());
            updateUser.setAccountId(user.getAccountId());
            updateUser.setIdentity(user.getIdentity());
            updateUser.setAlipayNumber(user.getAlipayNumber());
            updateUser.setGmtModified(System.currentTimeMillis());

            UserExample userExample1 = new UserExample();
            userExample1.createCriteria()
                    .andIdEqualTo(user.getId());
            userMapper.updateByExampleSelective(updateUser, userExample1);
    }



    public User login(User user) {

        UserExample example = new UserExample();
        example.createCriteria()
                .andNameEqualTo(user.getName())
                .andPasswordEqualTo(user.getPassword());
        List<User> users = userMapper.selectByExample(example);

        if (users.size() == 1) {
            return users.get(0);
        } else {
            return null;
        }
    }


    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        UserExample example = new UserExample();
        example.createCriteria()
                .andIdentityEqualTo(1);
        Integer totalCount = (int) userMapper.countByExample(example);
        Integer totalPage; //总页数

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        if (page < 1) {
            page = 1;
        }

        paginationDTO.setPagination(totalPage, page);

        Integer offset = size * (page - 1);   //从何处开始呈现

        UserExample example1 = new UserExample();
        example1.createCriteria()
                .andIdentityEqualTo(UserTypeEnum.PAINTER.getId());
        example1.setOrderByClause("gmt_create desc");
        List<User> users = userMapper.selectByExampleWithRowbounds(example1, new RowBounds(offset, size));  //所有画师user

        List<UserDTO> userList = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = new UserDTO();


            PaintingsExample example2 = new PaintingsExample();
            example2.createCriteria()
                    .andPainterIdEqualTo(user.getId());
            example2.setOrderByClause("gmt_create desc");
            List<Paintings> paintingsList = paintingsMapper.selectByExampleWithRowbounds(example2, new RowBounds(0, 3));  //得到画师作品3张


            BeanUtils.copyProperties(user, userDTO);
            userDTO.setPaintingsList(paintingsList);
            userList.add(userDTO);
        }
        paginationDTO.setUserDTOs(userList);
        return paginationDTO;

    }

    public User getByName(String name) {

        UserExample example = new UserExample();
        example.createCriteria()
                .andNameEqualTo(name);
        List<User> users = userMapper.selectByExample(example);
        if (users.get(0) == null) {
            throw new CustomizeException(CustomizeErrorCode.USER_NOT_FOUND);
        } else {
            return users.get(0);
        }


    }

    public PaginationDTO listAll(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        UserExample example = new UserExample();
        example.createCriteria();
        Integer totalCount = (int) userMapper.countByExample(example);
        Integer totalPage; //总页数

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        if (page < 1) {
            page = 1;
        }

        paginationDTO.setPagination(totalPage, page);

        Integer offset = size * (page - 1);   //从何处开始呈现

        example.setOrderByClause("gmt_create desc");
        List<User> users = userMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));  //所有user

        List<UserDTO> userList = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = new UserDTO();


            BeanUtils.copyProperties(user, userDTO);
            userList.add(userDTO);
        }
        paginationDTO.setUserDTOs(userList);
        return paginationDTO;
    }

    public PaginationDTO listByApply(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();


        ApplyExample example3 = new ApplyExample();
        example3.createCriteria()
                .andStateEqualTo(ApplyStateEnum.PENDING_REVIEW.getId());
        List<Apply> applyList = applyMapper.selectByExample(example3);             //待审核表
        if (applyList.size() == 0) {
            return new PaginationDTO();
        }


        Set<Long> userId = applyList.stream().map(apply -> apply.getUserId()).collect(Collectors.toSet());//得到所有用户ID
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(userId);
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);

        Integer totalCount = (int) userMapper.countByExample(userExample);
        Integer totalPage; //总页数

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        if (page < 1) {
            page = 1;
        }

        paginationDTO.setPagination(totalPage, page);

        Integer offset = size * (page - 1);   //从何处开始呈现


        userExample.setOrderByClause("gmt_create desc");
        List<User> users = userMapper.selectByExampleWithRowbounds(userExample, new RowBounds(offset, size));  //所有申请user

        List<UserDTO> userList = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = new UserDTO();


            PaintingsExample example2 = new PaintingsExample();
            example2.createCriteria()
                    .andPainterIdEqualTo(user.getId());
            example2.setOrderByClause("gmt_create desc");
            List<Paintings> paintingsList = paintingsMapper.selectByExampleWithRowbounds(example2, new RowBounds(0, 3));  //得到作品3张


            BeanUtils.copyProperties(user, userDTO);
            userDTO.setPaintingsList(paintingsList);
            userList.add(userDTO);


        }
        paginationDTO.setUserDTOs(userList);
        return paginationDTO;

    }
}
