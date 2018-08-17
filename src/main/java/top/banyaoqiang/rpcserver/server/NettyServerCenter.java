package top.banyaoqiang.rpcserver.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import top.banyaoqiang.RPCApi.common.coder.RPCDecoder;
import top.banyaoqiang.RPCApi.common.coder.RPCEncoder;
import top.banyaoqiang.RPCApi.protocal.RPCRequest;
import top.banyaoqiang.rpcserver.register.ServiceRegister;
import top.banyaoqiang.rpcserver.server.handler.RPCServerHandler;
import top.banyaoqiang.rpcserver.server.handler.test.TimeServerHandler;

/**
 * Created by 班耀强 on 2018/7/21
 */
public class NettyServerCenter {
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
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyServerCenter(54321);
    }
}