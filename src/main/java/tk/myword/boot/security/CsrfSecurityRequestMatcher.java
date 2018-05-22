package tk.myword.boot.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Pattern;
/*
*排除CSRF保护策略
* */
public class CsrfSecurityRequestMatcher implements RequestMatcher {

    protected Logger logger = LoggerFactory.getLogger(CsrfSecurityRequestMatcher.class);

    private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
    private List<String> execluderUrls;

    @Override
    public boolean matches(HttpServletRequest httpServletRequest) {

        if(execluderUrls != null && execluderUrls.size() > 0){
            String servletPath = httpServletRequest.getServletPath();
            for(String url : execluderUrls){
                if(servletPath.contains(url)){
                    logger.info("+++" + servletPath);
                    return false;
                }

            }
        }
        return !allowedMethods.matcher(httpServletRequest.getMethod()).matches();
    }

    public List<String> getExecluderUrls() {
        return execluderUrls;
    }

    public void setExecluderUrls(List<String> execluderUrls) {
        this.execluderUrls = execluderUrls;
    }
}
