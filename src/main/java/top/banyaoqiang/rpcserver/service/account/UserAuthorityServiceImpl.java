package top.banyaoqiang.rpcserver.service.account;

import top.banyaoqiang.RPCApi.api.UserAuthorityService;
import top.banyaoqiang.RPCApi.entity.OperationResult;
import top.banyaoqiang.rpcserver.annotation.RPCService;

/**
 * Created by 班耀强 on 2018/8/5
 */
@RPCService(UserAuthorityService.class)
public class UserAuthorityServiceImpl implements UserAuthorityService {

    @Override
    public OperationResult login(int i, String s) {
        return OperationResult.success();
    }
}
