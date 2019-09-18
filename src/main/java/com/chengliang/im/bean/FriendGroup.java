package com.chengliang.im.bean;

import lombok.Data;

import java.util.List;

/**
 * @author chengliang
 */
@Data
public class FriendGroup {

    private Integer id;
    private String groupname;
    private Integer online;
    private List<User> list;


}
