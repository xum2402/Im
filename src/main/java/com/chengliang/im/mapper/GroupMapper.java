package com.chengliang.im.mapper;

import com.chengliang.im.bean.Group;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface GroupMapper extends Mapper<Group> {

    List<Group> selectGroupByUserId(Integer userId);

    int selectCountByGroupId(Integer id);
}
