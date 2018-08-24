package server;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.banyaoqiang.database.dao.UserMapper;
import top.banyaoqiang.database.mybatis.MybatisProxy;
import top.banyaoqiang.rpcserver.server.NettyServerCenter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 班耀强 on 2018/7/14
 */
public class RPCServerTest {
    private static final Logger logger = LoggerFactory.getLogger(RPCServerTest.class);

    @Test
    public void testServerCenter() {
        try {
            new NettyServerCenter(54321).run();
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
    }

    @Test
    public void testMybatis() {
        UserMapper mapper = MybatisProxy.create(UserMapper.class);
        Map<String, Object> param = new HashMap<>();
        param.put("name", "test13");
        param.put("password", "123456");
        param.put("phone", null);
        param.put("address", "a.b.x");
        param.put("education", "high school");
        param.put("birthday", new Date());

        Integer id = mapper.insertUser(param);
        System.out.println();
    }
}
