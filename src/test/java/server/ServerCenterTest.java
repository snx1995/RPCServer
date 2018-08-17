package server;

import top.banyaoqiang.rpcserver.server.NettyServerCenter;

/**
 * Created by 班耀强 on 2018/7/14
 */
public class ServerCenterTest {
    public static void main(String[] args) throws Exception {
        new NettyServerCenter(54321).run();
    }
}
