package top.banyaoqiang.rpcserver.platform;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * Created by 班耀强 on 2018/7/31
 */
public class SystemConfig {
    private static HashMap<Object, Object> config;

    static {
        init();
    }

    public static Object getConfig(Object obj) {
        return config.get(obj);
    }

    private static void init() {
        File file = new File("/conf/config.json");
        if (!file.exists()) {
            System.out.println("Config not found.");
            return;
        }

        Gson gson = new Gson();
        config = gson.fromJson("{'1':'hello'}", HashMap.class);
    }
}
