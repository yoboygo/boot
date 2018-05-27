package ml.idream.config;

import ml.idream.security.CsrfSecurityRequestMatcher;
import ml.idream.security.SysLoginSuccessHandler;
import ml.idream.security.SysLogoutSuccessHandler;
import ml.idream.security.SysUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SysUserDetailService sysUserDetailService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .and()
                .formLogin().loginPage("/login")
                .successForwardUrl("/").successHandler(new SysLoginSuccessHandler())
                .failureUrl("/login?error").permitAll()
                .and()
                .logout().logoutSuccessUrl("/").logoutSuccessHandler(new SysLogoutSuccessHandler()).permitAll()
                .and()
                .authorizeRequests().antMatchers("/dy/**").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(sysUserDetailService);
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

    @Bean
    public static PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }
}
