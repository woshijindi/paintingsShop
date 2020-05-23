package com.example.paintingshop.dto;

import com.example.paintingshop.model.Notification;
import com.example.paintingshop.model.User;
import lombok.Data;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<DemandDTO> demandDTOs;
    private List<NotificationDTO> notificationDTOs;
    private List<UserDTO> userDTOs;
    private List<PaintingsDTO> paintingsDTOs;
    private List<EnlistDTO> enlistDTOs;
    private List<OrderDTO> orderDTOs;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;   //总页数

    public void setPagination(Integer totalPage, Integer page) {

        this.totalPage = totalPage;
        this.page = page;
        showPrevious = false;
        showNext = false;
        showFirstPage = false;
        showEndPage = false;
        if (totalPage != 0) {
            pages.add(page);
            for (int i = 1; i <= 3; i++) {
                if (page - i > 0) {
                    pages.add(0, page - i);
                }
                if (page + i <= totalPage) {
                    pages.add(page + i);
                }
            }

            //是否展示上一页
            if (page == 1) {
                showPrevious = false;
            } else {
                showPrevious = true;
            }
            //是否展示下一页
            if (page == totalPage) {
                showNext = false;
            } else {
                showNext = true;
            }

            //是否展示第一页
            if (pages.contains(1)) {
                showFirstPage = false;
            } else {
                showFirstPage = true;
            }
            //是否展示最后一页
            if (pages.contains(totalPage)) {
                showEndPage = false;
            } else {
                showEndPage = true;
            }
        }
    }
}
