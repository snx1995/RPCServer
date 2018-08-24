package top.banyaoqiang.rpcserver.service.platform;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.banyaoqiang.RPCApi.api.platform.PlatformService;
import top.banyaoqiang.RPCApi.entity.platform.WebFunction;
import top.banyaoqiang.database.dao.WebFunctionMapper;
import top.banyaoqiang.database.mybatis.MybatisProxy;
import top.banyaoqiang.rpcserver.annotation.RPCService;

import java.util.List;

/**
 * Created by 班耀强 on 2018/8/21
 */
@RPCService(PlatformService.class)
public class PlatformServiceImpl implements PlatformService {
    private static final Logger logger = LoggerFactory.getLogger(PlatformServiceImpl.class);

    @Override
    public List<WebFunction> getLimitedFunctions(int i) {
        WebFunctionMapper mapper = MybatisProxy.create(WebFunctionMapper.class);
        return mapper.selectLimitedFunctions(i);
    }
}
