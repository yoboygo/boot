package ml.idream.setting;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "securityconfig")
public class SecuritySetting {
    private String logoutSerccessUrl = "/logout";
    private String permitAll = "/api";
    private String deniedPage = "/deny";
    private String urlRoles;

    public String getLogoutSerccessUrl() {
        return logoutSerccessUrl;
    }

    public void setLogoutSerccessUrl(String logoutSerccessUrl) {
        this.logoutSerccessUrl = logoutSerccessUrl;
    }

    public String getPermitAll() {
        return permitAll;
    }

    public void setPermitAll(String permitAll) {
        this.permitAll = permitAll;
    }

    public String getDeniedPage() {
        return deniedPage;
    }

    public void setDeniedPage(String deniedPage) {
        this.deniedPage = deniedPage;
    }

    public String getUrlRoles() {
        return urlRoles;
    }

    public void setUrlRoles(String urlRoles) {
        this.urlRoles = urlRoles;
    }
}
