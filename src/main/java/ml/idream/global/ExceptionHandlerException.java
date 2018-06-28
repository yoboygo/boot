package ml.idream.global;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.exceptions.TemplateInputException;

/*
* 全局处理
* */
@ControllerAdvice
public class ExceptionHandlerException {

    private Logger logger = LoggerFactory.getLogger(ExceptionHandlerException.class);
    /*
    * 全局异常处理
    * */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResBean exception(Exception e, WebRequest request){
        ResBean ret = new ResBean();
        ret.setCode(GlobalConst.CODE_REQUEST_ERROR);
        ret.setMsg(GlobalConst.MSG_REQUEST_ERROR);
        logger.error("请求失败！",e);
        return ret;
    }

    /*
    * 为所有的RequestMapping添加属性
    * */
    @ModelAttribute
    public void addAttributes(Model model){
        model.addAttribute("extMsg","额外信息！");
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
