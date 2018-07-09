package ml.idream.blog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/blog")
public class BlogCtl {

    @RequestMapping(value="/",method = RequestMethod.GET)
    public String index (){
        return "/blog/blog-home";
    }

    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String toAddArticle(){
        return "/blog/add-article";
    }
}
