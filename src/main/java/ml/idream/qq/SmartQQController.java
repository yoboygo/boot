package ml.idream.qq;

import ml.idream.common.DreamResponse;
import ml.idream.manage.sys.user.SysUser;
import ml.idream.qq.common.QRCodeStatus;
import ml.idream.qq.entity.SmartQQAccount;
import ml.idream.qq.entity.SmartQQAccountList;
import ml.idream.qq.service.SmartQQLoginActor;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

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

    private static final Logger logger = LoggerFactory.getLogger(SmartQQController.class);

    /**
     * @Description 获取Base64二维码
     * @Param [request, response]
     * @return Base64的Image
     * @Author Aimy
     * @Date  2018/12/27 14:50
     **/
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value = "/QRCodeBase64")
    public DreamResponse<String> getQRCodeBase64(HttpServletRequest request, HttpServletResponse response) {
        DreamResponse<String> result = DreamResponse.defaultSuccess();
        try{
            CookieStore cookieStore = convertCookieStore(request);
            String qrCodeBase64 = smartQQLoginActor.getQRCodeBase64(cookieStore);
            setCookies(cookieStore,response);
            result.setData(qrCodeBase64);
        }catch (Exception e){
            logger.error("获取Base64的二维码失败！",e);
            result = DreamResponse.defaultFault();
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * @Description 检查是否登陆
     * @Param [request, response]
     * @return true|false
     * @Author Aimy
     * @Date  2018/12/27 14:56
     **/
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST,value = "/isLogin")
    public DreamResponse<Boolean> isLogin(HttpServletRequest request, HttpServletResponse response){
        DreamResponse<Boolean> result = DreamResponse.defaultSuccess();

        try{
            CookieStore cookieStore = convertCookieStore(request);
            SmartQQAccount smartQQAccount = smartQQLoginActor.isLogin(cookieStore);
            setCookies(cookieStore,response);
            boolean flag = smartQQAccount.getStatus() == QRCodeStatus.LOGIN;
            if(flag){
                String loginKey = getLoginKey();
                SmartQQAccountList.addSmartQQAccount(loginKey,smartQQAccount);
            }
            result.setData(flag);
        }catch (Exception e){
            logger.error("判断是否登陆失败！",e);
            result = DreamResponse.defaultFault();
            result.setMsg(e.getMessage());
        }

        return result;
    }

    /**
     * @Description list
     * @Param [request, response]
     * @return
     * @Author Aimy
     * @Date 2018/12/27 16:57
     **/
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/list")
    public DreamResponse<List> list(){
        DreamResponse result = DreamResponse.defaultSuccess();
        try{
            List<SmartQQAccount> datas = new ArrayList<>();
            String loginKey = getLoginKey();
            LinkedBlockingDeque<SmartQQAccount> smartQQAccountsQueue = SmartQQAccountList.getSmartQQAccount(loginKey);
            Iterator<SmartQQAccount> iterator = smartQQAccountsQueue.iterator();
            while (iterator.hasNext()){
                datas.add(iterator.next());
            }
            result.setData(datas);
        }catch (Exception e){
            logger.error("获取账号列表失败！",e);
            result = DreamResponse.defaultFault();
        }
        return result;
    }

    /**
     * @Description 将cookie 转换为 cookieStore
     * @Param
     * @return
     * @Author Aimy
     * @Date
     **/
    private CookieStore convertCookieStore(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        CookieStore cookieStore = new BasicCookieStore();
        for(Cookie cookie : cookies){
            if(!"qrsig".equals(cookie.getName())){
                continue;
            }
            BasicClientCookie item = new BasicClientCookie(cookie.getName(),cookie.getValue());
            item.setPath(StringUtils.isBlank(cookie.getPath()) ? "/" : cookie.getPath());
            item.setDomain(StringUtils.isBlank(cookie.getDomain()) ? "ptlogin2.qq.com" : cookie.getDomain());
            item.setVersion(cookie.getVersion());
            cookieStore.addCookie(item);
        }
        return cookieStore;
    }
    
    /**
     * @Description set-cookie
     * @Param [cookies, request]
     * @return void
     * @Author Aimy
     * @Date  
     **/
    private void setCookies(CookieStore cookieStore,HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Credentials","true");
        for(org.apache.http.cookie.Cookie cookie : cookieStore.getCookies()){
            Cookie item = new Cookie(cookie.getName(),cookie.getValue());
//            item.setDomain(cookie.getDomain());
//            item.setPath(cookie.getPath());
//            item.setVersion(cookie.getVersion());
//            item.setComment(cookie.getComment());
            response.addCookie(item);
        }
    }

    private String getLoginKey(){
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return String.valueOf(sysUser.getId());
    }
}
