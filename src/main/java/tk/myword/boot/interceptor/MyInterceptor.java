package tk.myword.boot.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTine = System.currentTimeMillis();
        request.setAttribute("startTime",startTine);
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long endTime = System.currentTimeMillis();
        System.out.println("本次请求耗时：" + (endTime - Long.parseLong(request.getAttribute("startTime").toString())) + " ms");
        super.postHandle(request, response, handler, modelAndView);
    }
}
