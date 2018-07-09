package ml.idream.global;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
* 动态页面
* */
@Controller
public class GlobalCtl {

    @RequestMapping(value = "/dynamic/**",method = RequestMethod.GET)
    public String dynamicPage(HttpServletRequest request, HttpServletResponse response){
        String ctx = request.getContextPath();
        String urlTmp = request.getRequestURI();
        String page = urlTmp.replace(ctx,"").replace("/dynamic","");
        return page;
    }

    @RequestMapping(value = "/error/{errorPage}",method = RequestMethod.GET)
    public String errorPage(@PathVariable("errorPage") String errorPage){

        return "/error/" + errorPage;
    }
}
