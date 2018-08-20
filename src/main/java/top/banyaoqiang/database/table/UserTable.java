package top.banyaoqiang.database.table;

import top.banyaoqiang.database.annotation.*;
/**
 * Created by 班耀强 on 2018/8/17
 */
@SqlTable("user")
public class UserTable {

    @SqlInteger("id")
    @SqlConstraint(AUTO_INCREMENT = true, PRIMARY_KEY = true)
    private int id;

    @SqlVarchar(value = "name", size = 20)
    @SqlConstraint(NOT_NULL = true)
    private String name;
}
