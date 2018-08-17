package top.banyaoqiang.rpcserver.service;

import top.banyaoqiang.RPCApi.api.ServerInfoService;
import top.banyaoqiang.RPCApi.entity.ServerInfo;
import top.banyaoqiang.rpcserver.annotation.RPCService;

/**
 * Created by 班耀强 on 2018/7/13
 */
@RPCService(ServerInfoService.class)
public class ServerInfoServiceImpl implements ServerInfoService {
    @Override
    public ServerInfo getServerInfo() {
        return new ServerInfo("BYQ PRC Server");
    }
}
