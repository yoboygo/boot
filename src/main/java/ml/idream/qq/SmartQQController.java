package ml.idream.qq;

import ml.idream.common.DreamResponse;
import ml.idream.qq.service.SmartQQLoginActor;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description QQ登陆控制器
 * @Author Aimy
 * @Date 2018/12/18 11:36
 **/
@Controller
@RequestMapping("/smart/qq")
public class SmartQQController {

    @Autowired
    private SmartQQLoginActor smartQQLoginActor;


    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value = "/QRCodeBase64")
    public DreamResponse<String> getQRCodeBase64(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CookieStore cookieStore = convertCookieStore(request);
        String qrCodeBase64 = smartQQLoginActor.getQRCodeBase64(cookieStore);
        setCookies(cookieStore,response);
        DreamResponse<String> result = DreamResponse.defaultSuccess();
        result.setData(qrCodeBase64);
        return result;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value = "/isLogin")
    public DreamResponse<Boolean> isLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CookieStore cookieStore = convertCookieStore(request);
        Boolean flag = smartQQLoginActor.isLogin(cookieStore);
        setCookies(cookieStore,response);
        DreamResponse<Boolean> result = DreamResponse.defaultSuccess();
        result.setData(flag);
        return result;
    }


    /**
     * @Description 将cookie 转换为 cookieStore
     * @Param
     * @return
     * @Author SongJianlong
     * @Date
     **/
    private CookieStore convertCookieStore(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        CookieStore cookieStore = new BasicCookieStore();
        for(Cookie cookie : cookies){
            BasicClientCookie item = new BasicClientCookie(cookie.getName(),cookie.getValue());
            item.setPath(StringUtils.isBlank(cookie.getPath()) ? "/" : cookie.getPath());
            item.setDomain(StringUtils.isBlank(cookie.getDomain()) ? "web2.qq.com" : cookie.getDomain());
            item.setVersion(cookie.getVersion());
            cookieStore.addCookie(item);
        }
        return cookieStore;
    }
    
    /**
     * @Description set-cookie
     * @Param [cookies, request]
     * @return void
     * @Author SongJianlong
     * @Date  
     **/
    private void setCookies(CookieStore cookieStore,HttpServletResponse response){
        for(org.apache.http.cookie.Cookie cookie : cookieStore.getCookies()){
            Cookie item = new Cookie(cookie.getName(),cookie.getValue());
            item.setDomain(cookie.getDomain());
            item.setPath(cookie.getPath());
            item.setVersion(cookie.getVersion());
            item.setComment(cookie.getComment());
            response.addCookie(item);
        }
    }
}
