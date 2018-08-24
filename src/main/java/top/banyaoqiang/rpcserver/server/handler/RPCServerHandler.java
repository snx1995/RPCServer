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
        if (msg instanceof RPCRequest) {
            RPCRequest request = (RPCRequest) msg;
            RPCResponse response = null;
            Object result = null;
            logger.debug("接收请求 {}", request.getInterfaceName());

            Object service = ServiceRegister.get(request.getInterfaceName());
            if (service == null) {
                logger.debug("ClassNotFoundException: {}", request.getInterfaceName());
                response = new RPCResponse(500, null);
                response.setException(new ClassNotFoundException(request.getInterfaceName()));
            } else {
                try {
                    Method method = service.getClass().getMethod(request.getMethodName(), request.getParameterTypes());
                    result = method.invoke(service, request.getParameters());
                    response = new RPCResponse(200, result);
                } catch (Exception e) {
                    logger.debug("捕获异常 {}", e.getClass().getName());
                    response = new RPCResponse(500, null);
                    response.setException(e);
                }
            }

            ctx.writeAndFlush(response).sync();
            ctx.close();
        } else throw new Exception("非法数据类型 " + msg.getClass().getName());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
