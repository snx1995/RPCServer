package top.banyaoqiang.database.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import top.banyaoqiang.database.DatabaseFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by 班耀强 on 2018/8/24
 */
public class MybatisProxy {

    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class<?>[] {clazz},
                ((proxy, method, args) -> {
                    SqlSessionFactory sessionFactory = DatabaseFactory.getSqlSessionFactory();
                    SqlSession session = null;
                    Object result = null;
                    try {
                        session = sessionFactory.openSession(true);
                        result = method.invoke(session.getMapper(clazz), args);
                    } finally {
                        if (session != null) session.close();
                    }
                    return result;
                })
        );
    }
}
