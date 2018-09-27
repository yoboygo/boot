package autologin.a114;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description 登陆114
 * @Author Aimy
 * @Date 2018/9/26 14:40
 **/
public class Login114 {

    private static final Logger logger = LoggerFactory.getLogger(Login114.class);

    public static void main(String[] args) throws Exception {

        Login114Processer processer = new Login114Processer(new Login114Config());
        boolean flag = processer.login();
        if(flag){
            logger.info("登陆成功！！");
        }
    }
}
