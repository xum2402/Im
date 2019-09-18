package com.chengliang.im.mapper;

import com.chengliang.im.bean.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author chengliang
 */
public interface UserMapper extends Mapper<User> {

    /**
     * 按照登录名称查找用户
     * @param loginName
     * @return
     */
    User selectUserByLoginName(String loginName);


    List<User> selectUserListByGroupId(Integer groupId);

    User selectUserByGroupId(Integer groupId);

    List<Integer> getGroupIdsByLoginName(String loginName);

}
