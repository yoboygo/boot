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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SysUserDetailService sysUserDetailService;

    @Autowired
    private SysLoginSuccessHandler sysLoginSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**","/configuration/security", "/swagger-ui.html",
                        "/index","/","/home","/error/**","/signup","/login",
                        "/js/**","/css/**","/images/**","/fonts/**","/pages/**","/plugins/**",
//                        "/**/css/**","/**/styles/**","/**/scripts/**","/**/js/**","/**/images/**","/**/fonts/**","/**/pages/**","/**/plugins/**",//通用静态资源路径
                        "/webjars/**","/blog/**","/layui/**",
                        "/store/**","/dynamic/**")
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(sysLoginSuccessHandler)
//                .successHandler(new SysLoginSuccessHandler("/home?login"))
                .failureUrl("/login?error").permitAll()
                .and()
                .headers().frameOptions().disable()
                .and()
                .logout().logoutSuccessHandler(new SysLogoutSuccessHandler("/home?logout"))
                .and().authorizeRequests().anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(sysUserDetailService);
//        auth.authenticationProvider()
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
        return new BCryptPasswordEncoder(10);
    }
}
