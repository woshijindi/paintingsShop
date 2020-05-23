package com.example.paintingshop.service;

import com.example.paintingshop.dto.DemandDTO;
import com.example.paintingshop.dto.PaginationDTO;
import com.example.paintingshop.enums.DemandStateEnum;
import com.example.paintingshop.exception.CustomizeErrorCode;
import com.example.paintingshop.exception.CustomizeException;
import com.example.paintingshop.mapper.DemandExtMapper;
import com.example.paintingshop.mapper.DemandMapper;
import com.example.paintingshop.mapper.UserMapper;
import com.example.paintingshop.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DemandService {


    @Autowired
    private DemandMapper demandMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DemandExtMapper demandExtMapper;

    public DemandDTO getById(Long id) {

        Demand demand = demandMapper.selectByPrimaryKey(id);
        if (demand == null) {
            throw new CustomizeException(CustomizeErrorCode.DEMAND_NOT_FOUND);
        }
        DemandDTO demandDTO = new DemandDTO();
        BeanUtils.copyProperties(demand, demandDTO);
        User user = userMapper.selectByPrimaryKey(demand.getCreator());
        demandDTO.setUser(user);
        return demandDTO;
    }


    public void createOrUpdate(Demand demand) {

        if (demand.getId() == null) {
            demand.setGmtCreate(System.currentTimeMillis());
            demand.setGmtModified(demand.getGmtCreate());
            demandMapper.insert(demand);
        } else {
            Demand updateDemand = new Demand();
            updateDemand.setGmtModified(System.currentTimeMillis());
            updateDemand.setTitle(demand.getTitle());
            updateDemand.setDescription(demand.getDescription());
            updateDemand.setPrice(demand.getPrice());
            updateDemand.setClosingDate(demand.getClosingDate());
            updateDemand.setSpecifications(demand.getSpecifications());
            updateDemand.setPurpose(demand.getPurpose());
            updateDemand.setPaintingsUrl(demand.getPaintingsUrl());
            updateDemand.setState(demand.getState());

            DemandExample example = new DemandExample();
            example.createCriteria()
                    .andIdEqualTo(demand.getId());
            int updated = demandMapper.updateByExampleSelective(updateDemand, example);

            if (updated != 1) {
                throw new CustomizeException(CustomizeErrorCode.DEMAND_NOT_FOUND);
            }
        }
    }


    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        DemandExample example = new DemandExample();
        example.createCriteria()
                .andStateEqualTo(DemandStateEnum.NOT_ACCEPTED.getId());           //仅展示未被接受的企划
        example.setOrderByClause("gmt_create desc");
        Integer totalCount = (int) demandMapper.countByExample(example);
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



        List<Demand> demands = demandMapper.selectByExampleWithBLOBsWithRowbounds(example, new RowBounds(offset, size));
        List<DemandDTO> demandDTOList = new ArrayList<>();


        for (Demand demand : demands) {
            User user = userMapper.selectByPrimaryKey(demand.getCreator());
            DemandDTO demandDTO = new DemandDTO();
            BeanUtils.copyProperties(demand, demandDTO);
            demandDTO.setUser(user);
            demandDTOList.add(demandDTO);
        }
        paginationDTO.setDemandDTOs(demandDTOList);
        return paginationDTO;

    }

    public PaginationDTO list(Long id,Integer page, Integer size){

        PaginationDTO paginationDTO =new PaginationDTO();
        DemandExample example = new DemandExample();
        example.createCriteria()
                .andCreatorEqualTo(id);
        Integer totalCount = (int)demandMapper.countByExample(example);  //总数
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
        paginationDTO.setPagination(totalPage,page);
        Integer offset = size * (page - 1);   //从何处开始呈现
        DemandExample example1 = new DemandExample();
        example1.createCriteria()
                .andCreatorEqualTo(id);
        example1.setOrderByClause("gmt_create desc");
        List<Demand> demands = demandMapper.selectByExampleWithBLOBsWithRowbounds(example1, new RowBounds(offset, size));
        List<DemandDTO> demandDTOList = new ArrayList<>();

        for (Demand demand : demands) {
            DemandDTO demandDTO = new DemandDTO();
            BeanUtils.copyProperties(demand, demandDTO);
            demandDTOList.add(demandDTO);
        }
        paginationDTO.setDemandDTOs(demandDTOList);


        return paginationDTO;

    }




    public void incEnlist(Long id) {
        Demand demand = new Demand();
        demand.setId(id);
        demand.setEnlistCount(1);
        demandExtMapper.incEnlist(demand);
    }

    public List<Demand> getListByCreator(Long id) {        //通过发起人查找企划

        DemandExample example = new DemandExample();
        example.createCriteria()
                .andCreatorEqualTo(id);
        example.setOrderByClause("gmt_create desc");
        List<Demand> demands = demandMapper.selectByExample(example);

        return demands;

    }

    public Long demandongoing(User user) {
        DemandExample example2 = new DemandExample();
        example2.createCriteria()
                .andStateEqualTo(DemandStateEnum.ACCEPTED.getId())
                .andCreatorEqualTo(user.getId());
        long demandOngoing = demandMapper.countByExample(example2);  //未读数

        return demandOngoing;
    }

    public void reduceEnlist(Long demandId) {
        Demand demand = new Demand();
        demand.setId(demandId);
        demand.setEnlistCount(1);
        demandExtMapper.reduceEnlist(demand);
    }


    public PaginationDTO listByMessage(Integer id, String message, Integer page, Integer size) {

        if (id == 0) {                       //按purpose查找

            PaginationDTO paginationDTO = new PaginationDTO();
            DemandExample example = new DemandExample();
            example.createCriteria()
                    .andPurposeEqualTo(message);
            Integer totalCount = (int) demandMapper.countByExample(example);
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
            List<Demand> demands = demandMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
            List<DemandDTO> demandDTOS = new ArrayList<>();

            for (Demand demand : demands) {
                DemandDTO demandDTO = new DemandDTO();
                BeanUtils.copyProperties(demand, demandDTO);
                demandDTOS.add(demandDTO);
            }
            paginationDTO.setDemandDTOs(demandDTOS);
            return paginationDTO;

        } else if (id == 1) {                           //按price查找


            PaginationDTO paginationDTO = new PaginationDTO();
            DemandExample example = new DemandExample();
            example.createCriteria()
                    .andPriceEqualTo(message);
            Integer totalCount = (int) demandMapper.countByExample(example);
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
            List<Demand> demands = demandMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
            List<DemandDTO> demandDTOS = new ArrayList<>();

            for (Demand demand : demands) {
                DemandDTO demandDTO = new DemandDTO();
                BeanUtils.copyProperties(demand, demandDTO);
                demandDTOS.add(demandDTO);
            }
            paginationDTO.setDemandDTOs(demandDTOS);
            return paginationDTO;

        } else {
            throw new CustomizeException(CustomizeErrorCode.TYPE_NOT_FOUND);
        }

    }

    public PaginationDTO listAll(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        DemandExample example = new DemandExample();
        example.createCriteria();        //展示所有企划
        example.setOrderByClause("gmt_create desc");
        Integer totalCount = (int) demandMapper.countByExample(example);
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



        List<Demand> demands = demandMapper.selectByExampleWithBLOBsWithRowbounds(example, new RowBounds(offset, size));
        List<DemandDTO> demandDTOList = new ArrayList<>();


        for (Demand demand : demands) {
            User user = userMapper.selectByPrimaryKey(demand.getCreator());
            DemandDTO demandDTO = new DemandDTO();
            BeanUtils.copyProperties(demand, demandDTO);
            demandDTO.setUser(user);
            demandDTOList.add(demandDTO);
        }
        paginationDTO.setDemandDTOs(demandDTOList);
        return paginationDTO;
    }
}
