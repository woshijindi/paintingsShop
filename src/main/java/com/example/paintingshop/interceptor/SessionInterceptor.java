package com.example.paintingshop.interceptor;


import com.example.paintingshop.mapper.UserMapper;
import com.example.paintingshop.model.User;
import com.example.paintingshop.model.UserExample;
import com.example.paintingshop.service.DemandService;
import com.example.paintingshop.service.EnlistService;
import com.example.paintingshop.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private EnlistService enlistService;
    @Autowired
    private DemandService demandService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria()
                            .andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);
                    if (users.size() != 0) {
                        request.getSession().setAttribute("user", users.get(0));
                        Long unReadCount = notificationService.unReadCount(users.get(0));
                        Long enlistOngoing = enlistService.enlistOngoing(users.get(0));
                        Long demandOngoing = demandService.demandongoing(users.get(0));

                        request.getSession().setAttribute("unReadCount",unReadCount);
                        request.getSession().setAttribute("enlistOngoing",enlistOngoing);
                        request.getSession().setAttribute("demandOngoing",demandOngoing);
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}