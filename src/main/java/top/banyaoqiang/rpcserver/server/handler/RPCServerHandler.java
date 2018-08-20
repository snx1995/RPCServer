package top.banyaoqiang.rpcserver.server.handler;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.banyaoqiang.RPCApi.protocal.RPCRequest;
import top.banyaoqiang.RPCApi.protocal.RPCResponse;
import top.banyaoqiang.rpcserver.register.ServiceRegister;

import java.lang.reflect.Method;

/**
 * Created by 班耀强 on 2018/8/8
 */
public class RPCServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(RPCServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (msg instanceof RPCRequest) {
                RPCRequest request = (RPCRequest) msg;
                logger.debug("接受请求 {}", request.getInterfaceName());
                Object service = ServiceRegister.get(request.getInterfaceName());
                if (service == null) throw new ClassNotFoundException(
                        "Interface " + request.getInterfaceName() + " not found"
                );
                Method method = service.getClass().getMethod(request.getMethodName(), request.getParameterTypes());
                Object result = method.invoke(service, request.getParameters());
                RPCResponse response = new RPCResponse(200, result);
                ctx.writeAndFlush(response).sync();
                logger.debug("响应已发送");
                ctx.close();
            } else throw new Exception("Not a RPCRequest!");
        } finally {
            logger.debug("finally");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
