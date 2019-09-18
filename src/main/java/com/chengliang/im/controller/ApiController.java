package com.chengliang.im.controller;

import com.alibaba.fastjson.JSONObject;
import com.chengliang.im.bean.JsonResult;
import com.chengliang.im.bean.User;
import com.chengliang.im.service.FriendGroupService;
import com.chengliang.im.service.GroupService;
import com.chengliang.im.service.UserService;
import com.chengliang.im.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private FriendGroupService friendGroupService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping("/init")
    public JsonResult init(String token){
        //获取当前用户信息
        User user = JSONObject.parseObject(JwtUtils.parseJwt(token).getSubject(), User.class);
        //
        Map<String,Object> init=new HashMap<>();
        //当前用户信息
        init.put("mine",user);
        //好友分组
        init.put("friend",friendGroupService.getFriendGroupListByUserId(user.getUserId()));
        //群组
        init.put("group",groupService.getGroupListByUserId(user.getUserId()));

        return JsonResult.success(init);
    }

    @GetMapping("/members")
    public JsonResult members(Integer id){
        //通过群组的ID查询该群组下的用户列表以及群组信息（群主、群人数）
        User user = userService.getUserByGroupId(id);
        List<User> userList = userService.getUserListByGroupId(id);
        int count = groupService.getCountByGroupId(id);
        Map<String,Object> map=new HashMap<>();
        //群主信息
        map.put("owner",user);
        map.put("list",userList);
        map.put("members",count);
        return JsonResult.success(map);
    }


}
