package autologin.a114;

import autologin.base.AbstractLoginProcesser;
import autologin.base.ILoginConfig;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @Description TODO
 * @Author Aimy
 * @Date 2018/9/26 15:52
 **/
public class Login114Processer extends AbstractLoginProcesser {
    private ILoginConfig loginConfig;

    public Login114Processer(ILoginConfig loginConfig) {
        this.loginConfig = loginConfig;
    }

    public boolean login() throws IOException, URISyntaxException {
        return super.login(loginConfig);
    }
}
