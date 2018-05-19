package tk.myword.boot.controlleradvice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/advice")
public class MyAdviceController  {

    @RequestMapping("/err")
    public String getSomething(@ModelAttribute("msg") String msg,DemoAdvice demoAdvice,String demo) throws Exception{
        System.out.println("advice error");
        throw new IllegalAccessException("非常抱歉，参数有误/" + "来自@ModelAttribute:" + msg + " address:" + demoAdvice.getAddress() + " demo" + demo);
    }
}
