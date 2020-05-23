package com.example.paintingshop.service;


import com.example.paintingshop.dto.NotificationDTO;
import com.example.paintingshop.dto.PaginationDTO;
import com.example.paintingshop.enums.NotificationStateEnum;
import com.example.paintingshop.enums.NotificationTypeEnum;
import com.example.paintingshop.exception.CustomizeErrorCode;
import com.example.paintingshop.exception.CustomizeException;
import com.example.paintingshop.mapper.DemandMapper;
import com.example.paintingshop.mapper.NotificationMapper;
import com.example.paintingshop.mapper.UserMapper;
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
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DemandMapper demandMapper;


    public PaginationDTO list(Long id, Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(id);
        Integer totalCount = (int) notificationMapper.countByExample(example);  //总数
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
        List<Notification> notificationList = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));  //通知列表


        if (notificationList.size() == 0) {
            return new PaginationDTO();
        }
        Set<Long> userId = notificationList.stream().map(notification -> notification.getNotifier()).collect(Collectors.toSet());//得到所有通知者ID
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(userId);
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        Set<Long> demandId = notificationList.stream().map(notification -> notification.getOuterId()).collect(Collectors.toSet());//得到所有需求ID
        List<Long> demandIds = new ArrayList<>();
        demandIds.addAll(demandId);
        DemandExample demandExample = new DemandExample();
        demandExample.createCriteria()
                .andIdIn(demandIds);
        List<Demand> demands = demandMapper.selectByExample(demandExample);
        Map<Long, Demand> demandMap = demands.stream().collect(Collectors.toMap(demand -> demand.getId(), demand -> demand));


        List<NotificationDTO> notificationDTOS = notificationList.stream().map(notification -> {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeMessage(NotificationTypeEnum.getValueByCode(notification.getType()));
            notificationDTO.setUser(userMap.get(notification.getNotifier()));           //通知者
            notificationDTO.setDemand(demandMap.get(notification.getOuterId()));
            return notificationDTO;
        }).collect(Collectors.toList());


        paginationDTO.setNotificationDTOs(notificationDTOS);

        return paginationDTO;


    }

    public Long unReadCount(User user) {
        NotificationExample example2 = new NotificationExample();
        example2.createCriteria()
                .andStateEqualTo(NotificationStateEnum.NOT_VIEW.getId())
                .andReceiverEqualTo(user.getId());
        long unReadCount = notificationMapper.countByExample(example2);  //未读数

        return unReadCount;

    }

    public int create(Notification notification) {

        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andNotifierEqualTo(notification.getNotifier())
                .andReceiverEqualTo(notification.getReceiver())
                .andStateEqualTo(notification.getState())
                .andTypeEqualTo(notification.getType())
                .andOuterIdEqualTo(notification.getOuterId());
        List<Notification> notifications = notificationMapper.selectByExample(example);
        if (notifications.size() == 0) {             //创建通知
            notificationMapper.insert(notification);  //写入通知表
            return 0;
        } else {                 //已经通知过一次了，对方还未查看
            return 1;
        }

    }


    public void update(Notification notification) {
        Notification updateNotification = new Notification();
        updateNotification.setState(notification.getState());


        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andIdEqualTo(notification.getId());

        notificationMapper.updateByExampleSelective(updateNotification, example);

    }
}
