package server;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.banyaoqiang.rpcserver.server.NettyServerCenter;

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
}
