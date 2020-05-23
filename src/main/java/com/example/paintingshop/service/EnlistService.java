package com.example.paintingshop.service;


import com.example.paintingshop.dto.EnlistDTO;
import com.example.paintingshop.dto.PaginationDTO;
import com.example.paintingshop.enums.EnlistStateEnum;
import com.example.paintingshop.enums.NotificationStateEnum;
import com.example.paintingshop.enums.NotificationTypeEnum;
import com.example.paintingshop.exception.CustomizeErrorCode;
import com.example.paintingshop.exception.CustomizeException;
import com.example.paintingshop.mapper.*;
import com.example.paintingshop.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EnlistService {

    @Autowired
    private EnlistMapper enlistMapper;
    @Autowired
    private DemandMapper demandMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PaintingsMapper paintingsMapper;
    @Autowired
    private NotificationService notificationService;


    public void insert(Enlist enlist) {

        Demand dbdemand = demandMapper.selectByPrimaryKey(enlist.getDemandId());
        if (dbdemand == null) {
            throw new CustomizeException(CustomizeErrorCode.DEMAND_NOT_FOUND);
        } else {
            enlistMapper.insert(enlist);   //写入应征表

            Notification notification = new Notification();
            notification.setNotifier(enlist.getUserId());
            notification.setReceiver(dbdemand.getCreator());
            notification.setOuterId(dbdemand.getId());
            notification.setGmtCreate(System.currentTimeMillis());
            notification.setType(NotificationTypeEnum.ENLIST.getId());
            notification.setState(NotificationStateEnum.NOT_VIEW.getId());
            notificationService.create(notification);   //写入通知表

        }
    }


    public List<EnlistDTO> listBytargetId(Long id) {

        EnlistExample example = new EnlistExample();
        example.createCriteria()
                .andDemandIdEqualTo(id);
        example.setOrderByClause("gmt_create desc");
        List<Enlist> enlistList = enlistMapper.selectByExample(example);  //得到应征表
        if (enlistList.size() == 0) {
            return new ArrayList<>();
        }

        Set<Long> commentators = enlistList.stream().map(enlist -> enlist.getUserId()).collect(Collectors.toSet());//得到所有应征者ID
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);     //得到应征者表
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));


        Set<Long> demands = enlistList.stream().map(enlist -> enlist.getDemandId()).collect(Collectors.toSet());//得到所有需求ID
        List<Long> demandIds = new ArrayList<>();
        demandIds.addAll(demands);
        DemandExample demandExample = new DemandExample();
        demandExample.createCriteria()
                .andIdIn(demandIds);
        List<Demand> demandList = demandMapper.selectByExample(demandExample);     //得到需求表
        Map<Long, Demand> demandMap = demandList.stream().collect(Collectors.toMap(demand -> demand.getId(), demand -> demand));





        List<EnlistDTO> enlistDTOS = enlistList.stream().map(enlist -> {   //赋值
            EnlistDTO enlistDTO = new EnlistDTO();

            PaintingsExample example1 = new PaintingsExample();
            example1.createCriteria()
                    .andPainterIdEqualTo(enlist.getUserId());
            example1.setOrderByClause("gmt_create desc");
            List<Paintings> paintingsList = paintingsMapper.selectByExampleWithRowbounds(example1, new RowBounds(0, 3));  //得到画师作品3张

            BeanUtils.copyProperties(enlist, enlistDTO);
            enlistDTO.setUser(userMap.get(enlist.getUserId()));
            enlistDTO.setDemand(demandMap.get(enlist.getDemandId()));
            enlistDTO.setPaintingsList(paintingsList);
            return enlistDTO;
        }).collect(Collectors.toList());

        return enlistDTOS;
    }

    public PaginationDTO list(Long id, Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();

        EnlistExample example = new EnlistExample();
        example.createCriteria()
                .andUserIdEqualTo(id);
        Integer totalCount = (int) enlistMapper.countByExample(example);

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
        List<Enlist> enlistList = enlistMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));  //列表


        if (enlistList.size() == 0) {
            return new PaginationDTO();
        }
        Set<Long> demandIdList = enlistList.stream().map(enlist -> enlist.getDemandId()).collect(Collectors.toSet());//得到所有需求ID
        List<Long> demandIds = new ArrayList<>();
        demandIds.addAll(demandIdList);

        DemandExample demandExample = new DemandExample();
        demandExample.createCriteria()
                .andIdIn(demandIds);
        List<Demand> demands = demandMapper.selectByExample(demandExample);     //得到需求表
        Map<Long, Demand> demandMap = demands.stream().collect(Collectors.toMap(demand -> demand.getId(), demand -> demand));



        List<EnlistDTO> enlistDTOS = new ArrayList<>();
        for (Enlist enlist : enlistList){
            EnlistDTO enlistDTO = new EnlistDTO();
            BeanUtils.copyProperties(enlist, enlistDTO);
            enlistDTO.setDemand(demandMap.get(enlist.getDemandId()));
            enlistDTOS.add(enlistDTO);
        }


        paginationDTO.setEnlistDTOs(enlistDTOS);

        return paginationDTO;

    }

    public EnlistDTO getById(Long id) {

        Enlist enlist = enlistMapper.selectByPrimaryKey(id);
        if (enlist == null) {
            throw new CustomizeException(CustomizeErrorCode.ENLIST_NOT_FOUND);
        }
        EnlistDTO enlistDTO = new EnlistDTO();
        BeanUtils.copyProperties(enlist, enlistDTO);
        Demand demand = demandMapper.selectByPrimaryKey(enlist.getDemandId());
        User user = userMapper.selectByPrimaryKey(demand.getCreator());
        enlistDTO.setDemand(demand);
        enlistDTO.setUser(user);
        return enlistDTO;


    }

    public Long enlistOngoing(User user) {
        EnlistExample example2 = new EnlistExample();
        example2.createCriteria()
                .andStateEqualTo(EnlistStateEnum.ACCEPTED.getId())
                .andUserIdEqualTo(user.getId());
        long enlistOngoing = enlistMapper.countByExample(example2);  //未读数

        return enlistOngoing;
    }

    public void update(Enlist enlist) {
        Enlist updateEnlist = new Enlist();
        updateEnlist.setGmtModified(System.currentTimeMillis());
        updateEnlist.setState(enlist.getState());
        updateEnlist.setClosingDate(enlist.getClosingDate());
        updateEnlist.setContent(enlist.getContent());
        updateEnlist.setPrice(enlist.getPrice());

        EnlistExample example = new EnlistExample();
        example.createCriteria()
                .andIdEqualTo(enlist.getId());
        int updated = enlistMapper.updateByExampleSelective(updateEnlist, example);

        if (updated != 1) {
            throw new CustomizeException(CustomizeErrorCode.ENLIST_NOT_FOUND);
        }
    }
}
