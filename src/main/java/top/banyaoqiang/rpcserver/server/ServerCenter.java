package top.banyaoqiang.rpcserver.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import top.banyaoqiang.rpcserver.annotation.RPCService;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 班耀强 on 2018/7/13
 */
public class ServerCenter {
    private static HashMap<String, Class<?>> services = new HashMap<>();

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("service_context.xml");
        Map<String, Object> serviceMap = context.getBeansWithAnnotation(RPCService.class);
        for (Object o : serviceMap.values()) {
            System.out.println(o.getClass().getName());
            services.put(o.getClass().getName(), o.getClass().getAnnotation(RPCService.class).value());
        }
        System.out.println("=========================================");
        Class<?> c = services.get("top.banyaoqiang.rpcserver.service.account.UserAuthorityServiceImpl");
        try {
            Method method = c.getMethod("login", new Class<?>[]{int.class, String.class});
            System.out.println(method.invoke(c.newInstance(), 1, "test"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
