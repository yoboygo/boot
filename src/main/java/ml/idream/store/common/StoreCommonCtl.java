package ml.idream.store.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/*
* wap
* */
@Controller
@RequestMapping("/store")
public class StoreCommonCtl {

    /*
    * 动态页面
    * */
    @RequestMapping("/dynamic/{platform}/{page}")
    public String storeCommon(@PathVariable("platform") String platform,@PathVariable("page") String page){

        return "/store/" + platform + "/" + page;
    }

}
