package net.keihong.web.imapp.push;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import net.keihong.web.imapp.push.provider.GsonProvider;
import net.keihong.web.imapp.push.service.AccountService;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.logging.Logger;

/**
 * Created by ${KeiHong}
 * on 2017/6/16 at 23:12.
 * Description:
 */
public class Application extends ResourceConfig{

    public Application() {
        //注册逻辑处理的包名（响应请求的）
        packages(AccountService.class.getPackage().getName());
        //等同于"net.keihong.web.imapp.push.service",上诉使用反射机制，更安全

        // 注册Json解析器
        // register(JacksonJsonProvider.class);
        // 替换解析器为Gson
        register(GsonProvider.class);

        //注册 日志打印输出
        register(Logger.class);
    }
}
