package ml.idream.security;

import ml.idream.sys.role.SysRole;
import ml.idream.sys.role.SysRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class SysInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private SysRoleDao roleDao;
    //key:url,value:List<role>
    private HashMap<String,Collection<ConfigAttribute>> rightMap = new HashMap<String,Collection<ConfigAttribute>>();

    @PostConstruct
    public void init(){
        rightMap.clear();
        List<SysRole> roles = roleDao.findAll();
        if(roles == null)  return;
        for (SysRole role : roles) {
            role.getPermissions().forEach( p -> {
                Collection<ConfigAttribute> caList = rightMap.get(p.getUrl());
                if(caList == null){
                    caList = new ArrayList<>();
                }
                SecurityConfig sc = new SecurityConfig(role.getName());
                if(!caList.contains(sc)){
                    caList.add(sc);
                    rightMap.put(p.getUrl(),caList);
                }
            });
        }
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        init();
        HttpServletRequest request = ((FilterInvocation)object).getHttpRequest();
        for(Map.Entry<String,Collection<ConfigAttribute>> entry : rightMap.entrySet()){
            AntPathRequestMatcher matcher = new AntPathRequestMatcher(entry.getKey());
            if(matcher.matches(request)){
                return entry.getValue();
            }
        }
        return new ArrayList<ConfigAttribute>();
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        init();
        Collection<ConfigAttribute> ret = new ArrayList<>();
        List<SysRole> roles = roleDao.findAll();
        if (roles == null) return ret;

        for (SysRole role : roles) {
            ret.add(new SecurityConfig(role.getName()));
        }
        return ret;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
