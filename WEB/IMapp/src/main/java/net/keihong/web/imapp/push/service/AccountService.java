package net.keihong.web.imapp.push.service;

import net.keihong.web.imapp.push.bean.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by ${KeiHong}
 * on 2017/6/16 at 23:18.
 * Description:
 */

/*
 * api地址： localhost/api/account/...
 */
@Path("/account")
public class AccountService {

    /**
     * get请求 响应地址：localhost/api/account/login
     * @return
     */
    @GET
    @Path("/login")
    //指定请求与返回的响应体body为JSON
    @Consumes(MediaType.APPLICATION_JSON)   //请求内容格式：json
    @Produces(MediaType.APPLICATION_JSON)   //返回格式：json
    public User loginPost(){
        return new User("陈其康",0);
        //通过反射获得方法名：AccountService.class.getDeclaredMethods()[0].getName();
    }

    @POST
    @Path("/login")
    @Produces("text/plain;charset=utf-8")
    public String loginGet(){
        return "你申请了登陆 You are logining.";
        //通过反射获得方法名：AccountService.class.getDeclaredMethods()[0].getName();
    }


}
