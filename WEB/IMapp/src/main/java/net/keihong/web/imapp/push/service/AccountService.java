package net.keihong.web.imapp.push.service;

import net.keihong.web.imapp.push.bean.api.account.RegisterModel;
import net.keihong.web.imapp.push.bean.db.User;

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

    // 注册
    @POST
    @Path("/register")
    // 指定请求与返回的相应体为JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RegisterModel register(RegisterModel model) {
        model.setName("注册成功");
        return model;
    }
}
