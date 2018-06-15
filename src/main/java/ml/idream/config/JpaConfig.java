package ml.idream.config;

import org.springframework.context.annotation.Bean;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;

/*
* 由JPA改为myBatis
* JAP 配置
* */
//@Order(Ordered.HIGHEST_PRECEDENCE)
//@Configuration
//@EnableTransactionManagement(proxyTargetClass = true)
//@EnableJpaRepositories(basePackages = "ml.idream")
//@ComponentScan(basePackages = "ml.idream")
public class JpaConfig {

    @Bean
    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor(){
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
