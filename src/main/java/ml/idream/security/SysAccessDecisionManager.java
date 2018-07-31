package ml.idream.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

@Service
public class SysAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        if(null == configAttributes || configAttributes.size() == 0){
            //如果配置表为空，则默认所有用户都可以访问任何路径
            //只要不为空，就按照配置表中的配置执行
            return ;
        }
        for(Iterator<ConfigAttribute> iter = configAttributes.iterator();iter.hasNext();){
            ConfigAttribute c = iter.next();
            String needRole = c.getAttribute();
            if("ROLE_ADMIN".equals(needRole)){//可以访问所有地址
                return;
            }
            for(GrantedAuthority ga : authentication.getAuthorities()){
                if(needRole.equals(ga.getAuthority())){
                    return ;
                }
            }
        }
        throw new AccessDeniedException("no rigth");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
