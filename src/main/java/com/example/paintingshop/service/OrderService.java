package com.example.paintingshop.service;


import com.example.paintingshop.dto.EnlistDTO;
import com.example.paintingshop.dto.OrderDTO;
import com.example.paintingshop.dto.PaginationDTO;
import com.example.paintingshop.enums.NotificationStateEnum;
import com.example.paintingshop.enums.NotificationTypeEnum;
import com.example.paintingshop.mapper.*;
import com.example.paintingshop.model.*;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.dc.pr.PRError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private DemandMapper demandMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EnlistMapper enlistMapper;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private EvaluateMapper evaluateMapper;


    //发起的订单
    public PaginationDTO listByCustomerId(Long id, Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();

        OrderExample example = new OrderExample();
        example.createCriteria()
                .andCustomerIdEqualTo(id);
        Integer totalCount = (int) orderMapper.countByExample(example);

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
        List<Order> orderList = orderMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));  //订单列表


        if (orderList.size() == 0) {
            return new PaginationDTO();
        }

        Set<Long> userId = orderList.stream().map(order -> order.getPainterId()).collect(Collectors.toSet());//得到所有画师ID
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(userId);
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));


        Set<Long> demandId = orderList.stream().map(order -> order.getDemandId()).collect(Collectors.toSet());//得到所有需求ID
        List<Long> demandIds = new ArrayList<>();
        demandIds.addAll(demandId);
        DemandExample demandExample = new DemandExample();
        demandExample.createCriteria()
                .andIdIn(demandIds);
        List<Demand> demands = demandMapper.selectByExample(demandExample);
        Map<Long, Demand> demandMap = demands.stream().collect(Collectors.toMap(demand -> demand.getId(), demand -> demand));


        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orderList) {
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(order, orderDTO);


            EnlistExample example1 = new EnlistExample();
            example1.createCriteria()
                    .andDemandIdEqualTo(order.getDemandId())
                    .andUserIdEqualTo(order.getPainterId());
            List<Enlist> enlistList = enlistMapper.selectByExample(example1);  //获得该订单应征信息


            if (order.getState() == 4) {
                EvaluateExample example2 = new EvaluateExample();
                example2.createCriteria()
                        .andOrderIdEqualTo(order.getId());
                List<Evaluate> evaluateList = evaluateMapper.selectByExample(example2);//获得评价信息
                if (evaluateList.size() != 0) {
                    orderDTO.setEvaluate(evaluateList.get(0));    //插入评价信息
                }
            }


            orderDTO.setDemand(demandMap.get(order.getDemandId()));
            orderDTO.setUser(userMap.get(order.getPainterId()));     //画师
            orderDTO.setEnlist(enlistList.get(0));

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date day = new Date();
            String dayString = df.format(day);

            try {
                Date dt1 = df.parse(dayString);         //date类型的当前时间
                Date dt2 = df.parse(orderDTO.getEnlist().getClosingDate());         //date类型的完成时间

                if (dt1.getTime() > dt2.getTime())//比较时间大小,如果dt1大于dt2
                {
                    orderDTO.setOvertime(true);           //超时
                } else {
                    orderDTO.setOvertime(false);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            orderDTOS.add(orderDTO);
        }

        paginationDTO.setOrderDTOs(orderDTOS);

        return paginationDTO;
    }


    //应征订单
    public PaginationDTO listByPainterId(Long id, Integer page, Integer size) {


        PaginationDTO paginationDTO = new PaginationDTO();

        OrderExample example = new OrderExample();
        example.createCriteria()
                .andPainterIdEqualTo(id);
        Integer totalCount = (int) orderMapper.countByExample(example);

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
        List<Order> orderList = orderMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));  //订单列表

        if (orderList.size() == 0) {
            return new PaginationDTO();
        }


        Set<Long> userId = orderList.stream().map(order -> order.getCustomerId()).collect(Collectors.toSet());//得到所有企划方ID
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(userId);
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        Set<Long> demandId = orderList.stream().map(order -> order.getDemandId()).collect(Collectors.toSet());//得到所有需求ID
        List<Long> demandIds = new ArrayList<>();
        demandIds.addAll(demandId);
        DemandExample demandExample = new DemandExample();
        demandExample.createCriteria()
                .andIdIn(demandIds);
        List<Demand> demands = demandMapper.selectByExample(demandExample);
        Map<Long, Demand> demandMap = demands.stream().collect(Collectors.toMap(demand -> demand.getId(), demand -> demand));


        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orderList) {

            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(order, orderDTO);


            EnlistExample example1 = new EnlistExample();
            example1.createCriteria()
                    .andDemandIdEqualTo(order.getDemandId())
                    .andUserIdEqualTo(order.getPainterId());
            List<Enlist> enlistList = enlistMapper.selectByExample(example1);  //获得该订单应征信息
            if (enlistList.size() == 0) {
                continue;                 //若订单不存在，则出错了，跳过此错误
            }

            if (order.getState() == 4) {
                EvaluateExample example2 = new EvaluateExample();
                example2.createCriteria()
                        .andOrderIdEqualTo(order.getId());
                List<Evaluate> evaluateList = evaluateMapper.selectByExample(example2);//获得评价信息
                if (evaluateList.size() != 0) {
                    orderDTO.setEvaluate(evaluateList.get(0));    //插入评价信息

                }
            }

            orderDTO.setDemand(demandMap.get(order.getDemandId()));
            orderDTO.setUser(userMap.get(order.getCustomerId()));     //企划方
            orderDTO.setEnlist(enlistList.get(0));


            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date day = new Date();
            String dayString = df.format(day);

            try {
                Date dt1 = df.parse(dayString);         //date类型的当前时间
                Date dt2 = df.parse(orderDTO.getEnlist().getClosingDate());         //date类型的完成时间

                if (dt1.getTime() > dt2.getTime())//比较时间大小,如果dt1大于dt2
                {
                    orderDTO.setOvertime(true);           //超时
                } else {
                    orderDTO.setOvertime(false);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }


            orderDTOS.add(orderDTO);

        }


        paginationDTO.setOrderDTOs(orderDTOS);
        return paginationDTO;
    }

    public void create(Order order) {


        order.setGmtCreate(System.currentTimeMillis());
        order.setGmtModified(System.currentTimeMillis());
        orderMapper.insert(order);                 //写入订单表


        Notification notification = new Notification();
        notification.setNotifier(order.getCustomerId());             //通知者是企划方，接收者是画师方
        notification.setReceiver(order.getPainterId());
        notification.setOuterId(order.getDemandId());
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(NotificationTypeEnum.CHOICE_ENLIST.getId());
        notification.setState(NotificationStateEnum.NOT_VIEW.getId());
        notificationService.create(notification);                          //写入通知表

    }

    public PaginationDTO listByPainterId(Long id) {


        OrderExample example = new OrderExample();
        example.createCriteria()
                .andPainterIdEqualTo(id)
                .andStateEqualTo(4);


        example.setOrderByClause("gmt_create desc");
        List<Order> orderList = orderMapper.selectByExample(example);  //订单列表

        if (orderList.size() == 0) {
            return new PaginationDTO();
        }


        Set<Long> userId = orderList.stream().map(order -> order.getCustomerId()).collect(Collectors.toSet());//得到所有企划方ID
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(userId);
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        Set<Long> demandId = orderList.stream().map(order -> order.getDemandId()).collect(Collectors.toSet());//得到所有需求ID
        List<Long> demandIds = new ArrayList<>();
        demandIds.addAll(demandId);
        DemandExample demandExample = new DemandExample();
        demandExample.createCriteria()
                .andIdIn(demandIds);
        List<Demand> demands = demandMapper.selectByExample(demandExample);
        Map<Long, Demand> demandMap = demands.stream().collect(Collectors.toMap(demand -> demand.getId(), demand -> demand));


        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orderList) {

            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(order, orderDTO);


            EnlistExample example1 = new EnlistExample();
            example1.createCriteria()
                    .andDemandIdEqualTo(order.getDemandId())
                    .andUserIdEqualTo(order.getPainterId());
            List<Enlist> enlistList = enlistMapper.selectByExample(example1);  //获得该订单应征信息
            if (enlistList.size() == 0) {
                continue;                 //若订单不存在，则出错了，跳过此错误
            }
            if (order.getState() == 4) {
                EvaluateExample example2 = new EvaluateExample();
                example2.createCriteria()
                        .andOrderIdEqualTo(order.getId());
                List<Evaluate> evaluateList = evaluateMapper.selectByExample(example2);//获得评价信息
                if (evaluateList.size() != 0) {
                    orderDTO.setEvaluate(evaluateList.get(0));    //插入评价信息

                }
            }
            orderDTO.setDemand(demandMap.get(order.getDemandId()));
            orderDTO.setUser(userMap.get(order.getCustomerId()));     //企划方
            orderDTO.setEnlist(enlistList.get(0));

            orderDTOS.add(orderDTO);

        }
        PaginationDTO paginationDTO = new PaginationDTO();

        paginationDTO.setOrderDTOs(orderDTOS);
        return paginationDTO;

    }

    public PaginationDTO listAll(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();

        OrderExample example = new OrderExample();
        example.createCriteria();
        Integer totalCount = (int) orderMapper.countByExample(example);

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
        List<Order> orderList = orderMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));  //订单列表


        if (orderList.size() == 0) {
            return new PaginationDTO();
        }

        Set<Long> userId = orderList.stream().map(order -> order.getPainterId()).collect(Collectors.toSet());//得到所有画师ID
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(userId);
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));


        Set<Long> demandId = orderList.stream().map(order -> order.getDemandId()).collect(Collectors.toSet());//得到所有需求ID
        List<Long> demandIds = new ArrayList<>();
        demandIds.addAll(demandId);
        DemandExample demandExample = new DemandExample();
        demandExample.createCriteria()
                .andIdIn(demandIds);
        List<Demand> demands = demandMapper.selectByExample(demandExample);
        Map<Long, Demand> demandMap = demands.stream().collect(Collectors.toMap(demand -> demand.getId(), demand -> demand));


        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orderList) {
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(order, orderDTO);

            EnlistExample example1 = new EnlistExample();
            example1.createCriteria()
                    .andDemandIdEqualTo(order.getDemandId())
                    .andUserIdEqualTo(order.getPainterId());
            List<Enlist> enlistList = enlistMapper.selectByExample(example1);  //获得该订单应征信息


            if (order.getState() == 4) {
                EvaluateExample example2 = new EvaluateExample();
                example2.createCriteria()
                        .andOrderIdEqualTo(order.getId());
                List<Evaluate> evaluateList = evaluateMapper.selectByExample(example2);//获得评价信息
                if (evaluateList.size() != 0) {
                    orderDTO.setEvaluate(evaluateList.get(0));    //插入评价信息
                }
            }


            orderDTO.setDemand(demandMap.get(order.getDemandId()));
            orderDTO.setUser(userMap.get(order.getPainterId()));     //画师
            orderDTO.setEnlist(enlistList.get(0));


            orderDTOS.add(orderDTO);
        }

        paginationDTO.setOrderDTOs(orderDTOS);

        return paginationDTO;
    }

    public PaginationDTO listAllApply(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();

        OrderExample example = new OrderExample();
        example.or().andGmtPaidEqualTo(1);
        example.or().andGmtPaidEqualTo(2);
        example.or().andGmtPaidEqualTo(3);
        Integer totalCount = (int) orderMapper.countByExample(example);

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
        List<Order> orderList = orderMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));  //订单列表


        if (orderList.size() == 0) {
            return new PaginationDTO();
        }

        Set<Long> userId = orderList.stream().map(order -> order.getPainterId()).collect(Collectors.toSet());//得到所有画师ID
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(userId);
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));


        Set<Long> demandId = orderList.stream().map(order -> order.getDemandId()).collect(Collectors.toSet());//得到所有需求ID
        List<Long> demandIds = new ArrayList<>();
        demandIds.addAll(demandId);
        DemandExample demandExample = new DemandExample();
        demandExample.createCriteria()
                .andIdIn(demandIds);
        List<Demand> demands = demandMapper.selectByExample(demandExample);
        Map<Long, Demand> demandMap = demands.stream().collect(Collectors.toMap(demand -> demand.getId(), demand -> demand));


        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orderList) {
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(order, orderDTO);

//
//            EnlistExample example1 = new EnlistExample();
//            example1.createCriteria()
//                    .andDemandIdEqualTo(order.getDemandId())
//                    .andUserIdEqualTo(order.getPainterId());
//            List<Enlist> enlistList = enlistMapper.selectByExample(example1);  //获得该订单应征信息


//            if (order.getState() == 4) {
//                EvaluateExample example2 = new EvaluateExample();
//                example2.createCriteria()
//                        .andOrderIdEqualTo(order.getId());
//                List<Evaluate> evaluateList = evaluateMapper.selectByExample(example2);//获得评价信息
//                if (evaluateList.size() != 0) {
//                    orderDTO.setEvaluate(evaluateList.get(0));    //插入评价信息
//                }
//            }


            orderDTO.setDemand(demandMap.get(order.getDemandId()));
            orderDTO.setUser(userMap.get(order.getPainterId()));     //画师
//            orderDTO.setEnlist(enlistList.get(0));


            orderDTOS.add(orderDTO);
        }

        paginationDTO.setOrderDTOs(orderDTOS);

        return paginationDTO;

    }
}
