package top.banyaoqiang.rpcserver.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import top.banyaoqiang.rpcserver.annotation.RPCService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 班耀强 on 2018/8/9
 */
public final class ServiceRegister {
    private static final Logger logger = LoggerFactory.getLogger(ServiceRegister.class);
    private static final boolean DEBUG = true;
    private static final String CONTEXT_XML_PATH = "service_context.xml";

    private static HashMap<String, Object> services = new HashMap<>();

    /**
     * Scan all RPCService using Spring and save to the map.
     */
    public static void register() {

        ApplicationContext context = new ClassPathXmlApplicationContext(CONTEXT_XML_PATH);
        Map<String, Object> s = context.getBeansWithAnnotation(RPCService.class);
        for (Object o : s.values()) {
            services.put(
                    o.getClass().getAnnotation(RPCService.class).value().getName(),
                    o
            );
        }

        if (DEBUG) {
            for (String ss : services.keySet()) logger.debug("发现服务 {}", ss);
        }
    }

    public static void register(String name, Class<?> interfaceClass) {
        services.put(name, interfaceClass);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String name) {
        return (T) services.get(name);
    }

    public static boolean empty() {
        return services.isEmpty();
    }
}
