package com.example.paintingshop.service;


import com.example.paintingshop.dto.PaginationDTO;
import com.example.paintingshop.dto.PaintingsDTO;
import com.example.paintingshop.exception.CustomizeErrorCode;
import com.example.paintingshop.exception.CustomizeException;
import com.example.paintingshop.mapper.PaintingsMapper;
import com.example.paintingshop.mapper.UserMapper;
import com.example.paintingshop.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaintingsService {

    @Autowired
    private PaintingsMapper paintingsMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO listByMessage(Integer id, String message, Integer page, Integer size) {


        if (id == 0) {                       //按type查找

            PaginationDTO paginationDTO = new PaginationDTO();
            PaintingsExample example = new PaintingsExample();
            example.createCriteria()
                    .andTypeEqualTo(message);
            Integer totalCount = (int) paintingsMapper.countByExample(example);
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
            List<Paintings> paintings = paintingsMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
            List<PaintingsDTO> paintingsDTOS = new ArrayList<>();

            for (Paintings paintings1 : paintings) {
                PaintingsDTO paintingsDTO = new PaintingsDTO();
                BeanUtils.copyProperties(paintings1, paintingsDTO);
                paintingsDTOS.add(paintingsDTO);
            }
            paginationDTO.setPaintingsDTOs(paintingsDTOS);
            return paginationDTO;

        } else if (id == 1) {                  //按style查找


            PaginationDTO paginationDTO = new PaginationDTO();
            PaintingsExample example = new PaintingsExample();
            example.createCriteria()
                    .andStyleEqualTo(message);
            Integer totalCount = (int) paintingsMapper.countByExample(example);
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
            List<Paintings> paintings = paintingsMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
            List<PaintingsDTO> paintingsDTOS = new ArrayList<>();

            for (Paintings paintings1 : paintings) {
                PaintingsDTO paintingsDTO = new PaintingsDTO();
                BeanUtils.copyProperties(paintings1, paintingsDTO);
                paintingsDTOS.add(paintingsDTO);
            }
            paginationDTO.setPaintingsDTOs(paintingsDTOS);
            return paginationDTO;

        } else if (id == 2) {              //按method查找


            PaginationDTO paginationDTO = new PaginationDTO();
            PaintingsExample example = new PaintingsExample();
            example.createCriteria()
                    .andMethodEqualTo(message);
            Integer totalCount = (int) paintingsMapper.countByExample(example);
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
            List<Paintings> paintings = paintingsMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
            List<PaintingsDTO> paintingsDTOS = new ArrayList<>();

            for (Paintings paintings1 : paintings) {
                PaintingsDTO paintingsDTO = new PaintingsDTO();
                BeanUtils.copyProperties(paintings1, paintingsDTO);
                paintingsDTOS.add(paintingsDTO);
            }
            paginationDTO.setPaintingsDTOs(paintingsDTOS);
            return paginationDTO;

        } else {
            throw new CustomizeException(CustomizeErrorCode.TYPE_NOT_FOUND);
        }

    }


    public void insert(Paintings paintings) {
        paintings.setGmtCreate(System.currentTimeMillis());
        paintings.setGmtModified(paintings.getGmtCreate());
        paintingsMapper.insert(paintings);

    }

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = (int) paintingsMapper.countByExample(new PaintingsExample());
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

        PaintingsExample example = new PaintingsExample();
        example.setOrderByClause("gmt_create desc");
        List<Paintings> paintings = paintingsMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        List<PaintingsDTO> paintingsDTOS = new ArrayList<>();


        for (Paintings paintings1 : paintings) {
            User user = userMapper.selectByPrimaryKey(paintings1.getPainterId());
            PaintingsDTO paintingsDTO = new PaintingsDTO();
            BeanUtils.copyProperties(paintings1, paintingsDTO);
            paintingsDTO.setUser(user);
            paintingsDTOS.add(paintingsDTO);
        }
        paginationDTO.setPaintingsDTOs(paintingsDTOS);
        return paginationDTO;
    }

    //我的作品
    public PaginationDTO list(Long id, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();

        PaintingsExample example = new PaintingsExample();
        example.createCriteria()
                .andPainterIdEqualTo(id);
        Integer totalCount = (int) paintingsMapper.countByExample(example);

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
        List<Paintings> paintingsList = paintingsMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));  //作品列表


        if (paintingsList.size() == 0) {
            return new PaginationDTO();
        }

        List<PaintingsDTO> paintingsDTOS = new ArrayList<>();
        for (Paintings paintings : paintingsList) {
            PaintingsDTO paintingsDTO = new PaintingsDTO();
            BeanUtils.copyProperties(paintings, paintingsDTO);
            paintingsDTOS.add(paintingsDTO);
        }


        paginationDTO.setPaintingsDTOs(paintingsDTOS);

        return paginationDTO;

    }

    public List<Paintings> getListByPainterId(Long id) {

        PaintingsExample example = new PaintingsExample();
        example.createCriteria()
                .andPainterIdEqualTo(id);
        example.setOrderByClause("gmt_create desc");
        List<Paintings> paintings = paintingsMapper.selectByExample(example);

        return paintings;
    }


}
