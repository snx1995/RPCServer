package top.banyaoqiang.database.dao;

import top.banyaoqiang.RPCApi.entity.platform.WebFunction;

import java.util.List;

/**
 * Created by 班耀强 on 2018/8/21
 */
public interface WebFunctionMapper {
    List<WebFunction> selectAll();

    List<WebFunction> selectLimitedFunctions(int id);
}
