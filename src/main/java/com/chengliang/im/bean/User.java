package com.chengliang.im.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author chengliang
 */
@Slf4j
@Data
public class User implements Serializable, UserDetails {

    @JsonProperty("id")
    private Integer userId;

    @JsonIgnore
    private String userNickName;
    @JsonIgnore
    private String userLoginName;
    @JsonIgnore
    private String userPassword;
    private Integer userSex;
    @JsonProperty("sign")
    private String userSign;

    @JsonProperty("status")
    private String userOnlineStatus;
    @JsonProperty("avatar")
    private String userAvatar;
    @JsonIgnore
    private Integer userStatus;


    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List list=new ArrayList();
        list.add(new SimpleGrantedAuthority("ROLE_user"));
        return list;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userNickName;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        log.info("用户状态{}",userStatus.equals(1));
        return userStatus == 1;
    }

}
