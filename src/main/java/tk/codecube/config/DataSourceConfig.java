package tk.codecube.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;


@Configuration
@EnableConfigurationProperties
public class DataSourceConfig {

    @Value("${jdbc-url}")
    private String debug;

    @Autowired
    private Environment environment;
    @Bean
    public YamlPropertiesFactoryBean yamlPropertiesFactoryBean(){
        return new YamlPropertiesFactoryBean();
    }

    @Bean
    public DataSource dataSource(){
        HikariDataSource hikarDataSource = new HikariDataSource();
        hikarDataSource.setJdbcUrl(environment.getProperty("jdbc-url"));
//        hikarDataSource.setDriverClassName(environment.getProperty("driver-class-name"));
        hikarDataSource.setDataSourceClassName(environment.getProperty("data-source-class-name"));
        hikarDataSource.setUsername(environment.getProperty("jdbc-username"));
        hikarDataSource.setPassword(environment.getProperty("jdbc-password"));
        return hikarDataSource;
    }

}
