package com.chengliang.im.bean;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

/**
 * @author chengliang
 */
public class JsonResult extends HashMap<String,Object> {

    /**
     * 追加自定义参数
     * @param key
     * @param value
     * @return
     */
    public JsonResult append(String key,Object value){
        this.put(key,value);
        return this;
    }

    public static JsonResult success(){
        return new JsonResult(0,"操作成功",null);
    }

    public static JsonResult success(String msg){
        return new JsonResult(0,msg,null);
    }

    public static JsonResult success(Object data){
        return new JsonResult(0,"操作成功",data);
    }

    public static JsonResult success(int code,String msg){
        return new JsonResult(code,msg,null);
    }

    public static JsonResult success(int code,Object data){
        return new JsonResult(code,"操作成功",data);
    }

    public static JsonResult success(int code,String msg,Object data){
        return new JsonResult(code,msg,data);
    }

    public static JsonResult error(){
        return new JsonResult(500,"操作失败",null);
    }

    public static JsonResult error(String msg){
        return new JsonResult(500,msg,null);
    }

    private JsonResult(int code,String msg,Object data){
        this.put("code",code);
        this.put("msg",msg);
        this.put("data",data);
    }

    /**
     * 转换为JSON字符串
     * @return
     */
    public String toJsonString(){
        return JSONObject.toJSONString(this);
    }

}
