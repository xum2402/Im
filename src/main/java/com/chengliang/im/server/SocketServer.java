package com.chengliang.im.server;

import com.alibaba.fastjson.JSONObject;
import com.chengliang.im.bean.Msg;
import com.chengliang.im.bean.MyJson;
import com.chengliang.im.mapper.UserMapper;
import com.chengliang.im.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * socket服务端
 * @author chengliang
 */
@Slf4j
@Component
@ServerEndpoint("/socket/{loginname}")
public class SocketServer {



    private UserService userService;

    /**
     * 在线人数
     */
    private static int onlineCount=0;

    /**
     * 用户集合
     */
    private static CopyOnWriteArraySet<SocketServer> currUserSet=new CopyOnWriteArraySet();

    /**
     * 通过session会话对象来发送与接收消息
     */
    private Session session;

    /**
     * 登录名
     */
    private String loginname;

    /**
     * 当前用户在哪些群中
     */
    private List<Integer> groupIds;

    //此处是解决无法注入的关键
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SocketServer.applicationContext = applicationContext;
    }
    /**
     * 服务器与客户端建立连接时 执行该方法
     * @param session
     * @param loginname
     */
    @OnOpen
    public  void  open(Session session,@PathParam("loginname") String loginname){
        //将当前用户添加到 集合中
        currUserSet.add(this);
        //添加在线人数 +1
        addOnlineCount();
        //赋值session对象
        this.session=session;
        //登录名
        this.loginname=loginname;
        //查询该用户有哪些群
        System.out.println("-------------"+userService);
        //获取UserService
        groupIds=applicationContext.getBean(UserService.class).getGroupIdsByLoginName(loginname);
        //
        log.debug("有用户尝试连接服务器:{}",loginname);
    }

    /**
     * 断开连接、关闭连接
     */
    @OnClose
    public void close(){
        //减少在线人数
        reduceOnlineCount();
        //将用户从集合中移除
        currUserSet.remove(this);
        log.warn("有用户断开连接，当前在线人数{}",currUserSet.size());
    }

    @OnError
    public void error(Session session,Throwable error){
        error.printStackTrace();
    }

    @OnMessage
    public void msg(String msg,Session session) throws IOException {
        log.info("客户端发送的消息{}",msg);
        //将消息转换为对象
        MyJson mj= JSONObject.parseObject(msg, MyJson.class);
        //判断是否是群组消息
        if("group".equals(mj.getTo().getType())){
            //遍历所有的用户
            for (SocketServer socketServer : currUserSet) {
                //判断用户是否在该群组中
                if(socketServer != this && socketServer.groupIds.contains(mj.getTo().getId())){
                    //在群中发送消息
                    socketServer.session.getBasicRemote().sendText(msg);
                }
            }
            return;
        }
        //群发消息
        for (SocketServer socketServer : currUserSet) {
            //判断是否是群聊
            //找到该用户发送消息（jerry2）
            System.out.println(socketServer.loginname+"--------------"+mj.getTo().getUsername());
            if(socketServer.loginname.equals(mj.getTo().getUsername())){
                log.info("发送消息给{}，内容为:{}",loginname,mj.getTo().getContent());
                socketServer.sendMsg(msg);
            }
        }


    }

    /**
     * 发送消息
     * @param msg
     */
    public void sendMsg(String msg){
        try {
            this.session.getBasicRemote().sendText(msg);
            log.info("发送信息成功");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("发送消息失败");
        }
    }


    private static synchronized void addOnlineCount() {
        onlineCount++;
    }

    private static synchronized void reduceOnlineCount() {
        onlineCount--;
    }


}
