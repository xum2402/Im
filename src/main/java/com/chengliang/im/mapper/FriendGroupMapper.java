package com.chengliang.im.mapper;

import com.chengliang.im.bean.FriendGroup;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author chengliang
 */
public interface FriendGroupMapper extends Mapper<FriendGroup> {

    /**
     * 通过用户Id查询 好友分组
     * @param userId
     * @return
     */
    List<FriendGroup> selectFriendGroupByUserId(Integer userId);
}
