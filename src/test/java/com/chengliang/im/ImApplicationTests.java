package com.chengliang.im;

import com.chengliang.im.bean.FriendGroup;
import com.chengliang.im.bean.Group;
import com.chengliang.im.bean.User;
import com.chengliang.im.mapper.FriendGroupMapper;
import com.chengliang.im.mapper.GroupMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImApplicationTests {

//    @Autowired
//    private PasswordEncoder passwordEncoder;
    @Autowired
    private FriendGroupMapper friendGroupMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Test
    public void contextLoads() {
       /* String encode = passwordEncoder.encode("123");
        System.out.println(encode);*/
        List<FriendGroup> friendGroups = friendGroupMapper.selectFriendGroupByUserId(1);
        for (FriendGroup friendGroup : friendGroups) {
            System.out.println(friendGroup.getGroupname());
            System.out.println("---------------------");
            for (User user : friendGroup.getList()) {
                System.out.println(user);
            }
            System.out.println("****************************");
        }
    }

    @Test
    public void test2(){
        List<Group> groups = groupMapper.selectGroupByUserId(1);
        for (Group group : groups) {
            System.out.println(group.getGroupname());
        }
    }

}
