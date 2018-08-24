package top.banyaoqiang.database.dao;

import top.banyaoqiang.RPCApi.entity.OperationResult;
import top.banyaoqiang.RPCApi.entity.User;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by 班耀强 on 2018/8/21
 */
public interface UserMapper {
    User selectUser(int id);

    User selectUserByName(String name);

    User loginById(Map<String, Object> param);

    User loginByName(Map<String, Object> param);

    ArrayList<User> selectAll();

    String selectPassword(int id);

    Integer insertUser(Map<String, Object> param);
}
