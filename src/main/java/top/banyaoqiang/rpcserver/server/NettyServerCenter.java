package top.banyaoqiang.rpcserver.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.banyaoqiang.RPCApi.common.coder.RPCDecoder;
import top.banyaoqiang.RPCApi.common.coder.RPCEncoder;
import top.banyaoqiang.RPCApi.protocal.RPCRequest;
import top.banyaoqiang.rpcserver.register.ServiceRegister;
import top.banyaoqiang.rpcserver.server.handler.RPCServerHandler;

/**
 * Created by 班耀强 on 2018/7/21
 */
public class NettyServerCenter {
    private static final Logger logger = LoggerFactory.getLogger(NettyServerCenter.class);

    private int port;

    public NettyServerCenter(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        ServiceRegister.register();

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            logger.debug("服务器正在启动...");
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast(
                                    new RPCEncoder(),
                                    new RPCDecoder(RPCRequest.class),
                                    new RPCServerHandler()
                            );
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture f = b.bind(port).sync();

            logger.debug("服务器已启动.");

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully().sync();
            bossGroup.shutdownGracefully().sync();
            logger.debug("服务器已关闭");
        }
    }

    public static void main(String[] args) {
        try {
            new NettyServerCenter(54321).run();
        } catch (Exception e) {
            logger.error("服务启动失败 {}", e.getMessage());
        }
    }
}