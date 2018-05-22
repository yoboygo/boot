package tk.myword.boot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecuritySetting securitySetting;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login").permitAll().successHandler(new LoginSuccessHandler())
                .and()
                .authorizeRequests().antMatchers(securitySetting.getPermitAll().split(",")).permitAll()
                .anyRequest().authenticated()
                //csrf
                .and().csrf()
                .requireCsrfProtectionMatcher(csrfSecurityRequestMatcher())
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and().logout().logoutSuccessUrl(securitySetting.getLogoutSerccessUrl())
                .and().exceptionHandling().accessDeniedPage(securitySetting.getDeniedPage());
//        .and().rememberMe().tokenValiditySeconds(86400).tokenRepository(tokenRepository());
    }

    /*
     * CSRF配置
     * */
    private CsrfSecurityRequestMatcher csrfSecurityRequestMatcher() {
        CsrfSecurityRequestMatcher csrfSecurityRequestMatcher = new CsrfSecurityRequestMatcher();
        List<String> list = new ArrayList<String>();
        list.add("/rest/");
        csrfSecurityRequestMatcher.setExecluderUrls(list);
        return csrfSecurityRequestMatcher;
    }

}
