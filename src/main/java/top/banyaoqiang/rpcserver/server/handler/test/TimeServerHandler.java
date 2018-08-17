package top.banyaoqiang.rpcserver.server.handler.test;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

/**
 * Created by 班耀强 on 2018/8/8
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf time = ctx.alloc().buffer(8);
        time.writeLong(System.currentTimeMillis());
        final ChannelFuture f = ctx.writeAndFlush(time);
        f.addListener((future) -> {
            assert f == future;
            ctx.close();
        });
    }
}
