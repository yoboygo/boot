package autologin.base;

import java.io.IOException;
import java.net.URISyntaxException;

//处理的登陆的接口
public interface ILoginProcesser {
    //登陆方法
    boolean login(ILoginConfig loginConfig) throws URISyntaxException, IOException;

}
