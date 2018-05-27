package ml.idream.global;

import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/*
* 全局处理
* */
@ControllerAdvice
public class ExceptionHandlerException {

    /*
    * 全局异常处理
    * */
    @ExceptionHandler(value = Exception.class)
    public ModelAndView exception(Exception e, WebRequest request){
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage",e.getMessage());
        return modelAndView;
    }

    /*
    * 为所有的RequestMapping添加属性
    * */
    @ModelAttribute
    public void addAttributes(Model model){
        model.addAttribute("msg","额外信息！");
    }

    /*
    * 全局修改请求
    * */
    @InitBinder
    public void iniBinder(WebDataBinder webDataBinder){
        /*
        * 此方法只针对Object中的属性
        * */
        webDataBinder.setDisallowedFields("address","demo");
    }
}
