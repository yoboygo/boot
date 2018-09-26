package autologin.a114;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @Description 登陆114
 * @Author Aimy
 * @Date 2018/9/26 14:40
 **/
public class Login114 {

    public static void main(String[] args) throws IOException, URISyntaxException {
        Login114Processer processer = new Login114Processer(new Login114Config());
        boolean flag = processer.login();
        if(flag){
            System.out.println("登陆成功！！");
        }
    }
}
