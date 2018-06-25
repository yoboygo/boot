package ml.idream.blog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/blog")
public class BlogCtl {

    @RequestMapping
    public String index (){
        return "/blog/blog-home";
    }

    @RequestMapping("/add")
    public String toAddArticle(){
        return "/blog/add-article";
    }
}
