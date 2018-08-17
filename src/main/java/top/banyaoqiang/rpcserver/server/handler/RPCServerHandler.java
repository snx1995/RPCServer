package top.banyaoqiang.rpcserver.server.handler;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import top.banyaoqiang.RPCApi.protocal.RPCRequest;
import top.banyaoqiang.RPCApi.protocal.RPCResponse;
import top.banyaoqiang.rpcserver.register.ServiceRegister;

import java.lang.reflect.Method;

/**
 * Created by 班耀强 on 2018/8/8
 */
public class RPCServerHandler extends ChannelInboundHandlerAdapter {
    private ChannelHandlerContext context = null;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Channel active");
        context = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Channel read");
        try {
            if (msg instanceof RPCRequest) {
                RPCRequest request = (RPCRequest) msg;
                Object service = ServiceRegister.get(request.getInterfaceName());
                if (service == null) throw new ClassNotFoundException(
                        "Interface " + request.getInterfaceName() + " not found"
                );
                Method method = service.getClass().getMethod(request.getMethodName(), request.getParameterTypes());
                Object result = method.invoke(service, request.getParameters());
                RPCResponse response = new RPCResponse(200, result);
                send(response).sync();
                System.out.println("response has been sent.");
                ctx.close();
            } else throw new Exception("Not a RPCRequest!");
        } finally {
            System.out.println("finally");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    public ChannelFuture send(RPCResponse response) {
        if (context != null) return context.writeAndFlush(response);
        return null;
    }
}
