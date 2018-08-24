package top.banyaoqiang.rpcserver.service.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.banyaoqiang.RPCApi.api.UserAuthorityService;
import top.banyaoqiang.RPCApi.entity.OperationResult;
import top.banyaoqiang.RPCApi.entity.User;
import top.banyaoqiang.database.Database;
import top.banyaoqiang.database.dao.UserMapper;
import top.banyaoqiang.database.mybatis.MybatisProxy;
import top.banyaoqiang.rpcserver.annotation.RPCService;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 班耀强 on 2018/8/5
 */
@RPCService(UserAuthorityService.class)
public class UserAuthorityServiceImpl implements UserAuthorityService {
    private static final Logger logger = LoggerFactory.getLogger(UserAuthorityServiceImpl.class);

    @Override
    public User login(Integer id, String passwd) {
        UserMapper mapper = MybatisProxy.create(UserMapper.class);
        Map<String, Object> param = new HashMap<>();
        User user = null;

        param.put("id", id);
        param.put("password", passwd);
        user = mapper.loginById(param);
        return user;
    }

    @Override
    public User login(String name, String passwd) {
        UserMapper mapper = MybatisProxy.create(UserMapper.class);
        Map<String, Object> param = new HashMap<>();
        User user = null;

        param.put("name", name);
        param.put("password", passwd);
        user = mapper.loginByName(param);
        return user;
    }

    @Override
    public User login(Integer id, String name, String passwd) {
        UserMapper mapper = MybatisProxy.create(UserMapper.class);

        Map<String, Object> param = new HashMap<>();
        User user = null;
        if (id != null) {
            param.put("id", id);
            param.put("password", passwd);
            user = mapper.loginById(param);
        } else if (name != null) {
            param.put("name", name);
            param.put("password", passwd);
            user = mapper.loginByName(param);
        }
        return user;
    }

    @Override
    public User register(Map<String, Object> map) {
        UserMapper mapper = MybatisProxy.create(UserMapper.class);
        User user = null;
        try {
            mapper.insertUser(map);
            String name = (String) map.get("name");
            user = mapper.selectUserByName(name);
        } catch (Exception e) {
            throw e;
        }
        return user;
    }

    @Override
    public User getUserInfo(int id) {
        UserMapper mapper = MybatisProxy.create(UserMapper.class);
        return mapper.selectUser(id);
    }

    @Override
    public User getUserInfo(String name) {
        UserMapper mapper = MybatisProxy.create(UserMapper.class);
        return mapper.selectUserByName(name);
    }

    private OperationResult loginByJDBC(int id, String passwd) {
        String sql = "select password from cloud_user where id=" + id;
        ResultSet rs = Database.execute(sql);
        String password = null;
        try {
            while (rs.next()) {
                if (password == null) password = rs.getString("password");
                else throw new Exception("用户查询多结果错误");
            }
        } catch (Exception e) {
            logger.error("执行login出错 {}", e.getMessage());
        }
        if (password != null && password.equals(passwd)) return OperationResult.success();
        else return new OperationResult(500, "密码错误");
    }
}
