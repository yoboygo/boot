package ml.idream.manage.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping(value = "/manage")
public class ManageCtl {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public String manage(){

        System.out.println(stringRedisTemplate.opsForHash().entries("contestImInitUserInfo"));

        stringRedisTemplate.opsForHash().put("Test","1","1");
        stringRedisTemplate.boundHashOps("Test").put("2","2");
        stringRedisTemplate.opsForHash().put("Test","3","3");

        Map<Object,Object> datas = stringRedisTemplate.opsForHash().entries("contestImInitUserInfo");
        for (Map.Entry<Object,Object> item : datas.entrySet()){
            System.out.println(item.getKey() + ":" + item.getValue());
        }

        return "/manage/manage-index";
    }

    /*
    * 管理员欢迎界面
    * */
    @RequestMapping(value = "/welcome",method = RequestMethod.GET)
    public String welcome(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        return "/manage/welcome";
    }
}
