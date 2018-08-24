package server.service;

import org.junit.Test;
import top.banyaoqiang.RPCApi.entity.User;
import top.banyaoqiang.rpcserver.service.account.UserAuthorityServiceImpl;

/**
 * Created by 班耀强 on 2018/8/24
 */
public class UserAuthorityServiceTest {
    private static final UserAuthorityServiceImpl service = new UserAuthorityServiceImpl();

    @Test
    public void testLogin() {

        User user = service.login(1, "byq", "123456789");
        User user1 = service.login(1, null, "123456789");
        User user2 = service.login(null, "byq", "123456789");
        User user3 = service.login(null, "root", "123456789");
        User user4 = service.login(null, null, "123456789");
        System.out.println();
    }
}
