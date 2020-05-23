package com.example.paintingshop.provider;

import com.example.paintingshop.dto.SocketMsgDTO;
import com.example.paintingshop.exception.CustomizeErrorCode;
import com.example.paintingshop.exception.CustomizeException;
import com.example.paintingshop.mapper.UserMapper;
import com.example.paintingshop.model.User;
import com.example.paintingshop.model.UserExample;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 *  * @Auther: zj
 *  * @Date: 2018/8/16 17:55
 *  * @Description: websocket的具体实现类
 *  * 使用springboot的唯一区别是要@Component声明下，而使用独立容器是由容器自己管理websocket的，
 *  * 但在springboot中连容器都是spring管理的。
 *     虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，
 *     所以可以用一个静态set保存起来。
 *  
 */
@ServerEndpoint(value = "/websocket/{nickId}")
@Component
public class MyWebSocket {
//用来存放每个客户端对应的MyWebSocket对象。

    public static MyWebSocket myWebSocket;
    @Autowired
    private UserMapper userMapper;

    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private String nickname;

    private static Map<String, Session> map = new HashMap<String, Session>();

    @PostConstruct
    public void init() {
        myWebSocket = this;
        myWebSocket.userMapper = this.userMapper;
    }


    /**
     *      * 连接建立成功调用的方法
     *     
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("nickId") Long nickId) {

        User user = myWebSocket.userMapper.selectByPrimaryKey(nickId);


        String numberId= Long.toString(user.getAccountId());

        this.session = session;
        this.nickname = user.getName();

        map.put(numberId, session);
        webSocketSet.add(this);          //加入set中

    }

    /**
     *      * 连接关闭调用的方法
     *     
     */

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);      //从set中删除
    }


    /**
     *      * 收到客户端消息后调用的方法
     *      *
     *      * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("nickId") Long nickId) {

        User user = myWebSocket.userMapper.selectByPrimaryKey(nickId);

        //从客户端传过来的数据是json数据，所以这里使用jackson进行转换为SocketMsg对象，
// 然后通过socketMsg的type进行判断是单聊还是群聊，进行相应的处理:
        ObjectMapper objectMapper = new ObjectMapper();
        SocketMsgDTO socketMsg;

        try {
            socketMsg = objectMapper.readValue(message, SocketMsgDTO.class);
            if (socketMsg.getType() == 1) {
//单聊.需要找到发送者和接受者.

                socketMsg.setFromUser(Long.toString(user.getAccountId()));//发送者.
                Session fromSession = map.get(socketMsg.getFromUser());
                Session toSession = map.get(socketMsg.getToUser());

                if (toSession != null) {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date day = new Date();
                    String dayString = df.format(day);
                    //发送给发送者.
                    fromSession.getAsyncRemote().sendText(nickname +"["+ dayString + "] : " + socketMsg.getMsg());
                    //发送给接受者.
                    toSession.getAsyncRemote().sendText(nickname +"["+ dayString + "] : " + socketMsg.getMsg());
                } else {

                    //发送给发送者.
                    fromSession.getAsyncRemote().sendText("系统消息：发送失败，对方不在线");
                }
            } else {
                //群发消息
//                broadcast(nickname + "(" + user.getAccountId() + "): " + socketMsg.getMsg());
                throw new CustomizeException(CustomizeErrorCode.USER_NOT_FOUND);
            }

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     *      * 发生错误时调用
     *      *
     *     
     */

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     *      * 群发自定义消息
     *      *
     */
    public void broadcast(String message) {
        for (MyWebSocket item : webSocketSet) {
            item.session.getAsyncRemote().sendText(message);//异步发送消息.
        }
    }
}
