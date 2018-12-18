package ml.idream.qq;

import ml.idream.common.DreamResponse;
import ml.idream.qq.service.SmartQQLoginActor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description TODO
 * @Author SongJianlong
 * @Date 2018/12/18 11:36
 **/
@Controller
@RequestMapping("/smart/qq")
public class SmartQQController {

    @Autowired
    private SmartQQLoginActor smartQQLoginActor;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,value = "/QRCode")
    public DreamResponse<String> getQRCode(){


        return null;
    }
}
