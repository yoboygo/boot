package tk.myword.boot.interceptor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("/myword")
public class MyController {

    @RequestMapping("/inter")
    @ResponseBody
    public String testInterceptor(){

        return "成功！";
    }
}
