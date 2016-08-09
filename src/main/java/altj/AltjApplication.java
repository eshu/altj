package altj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Собственно, запускалка приложения.
 * 
 * Аннотация @EnableConfigurationProperties позволяет использовать
 * параметры конфигурации из config/application.yaml
 * Подробнее тут:
 * http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-external-config
 */
@SpringBootApplication
@EnableConfigurationProperties
public class AltjApplication {
    public static void main(String[] args) {
        SpringApplication.run(AltjApplication.class, args);
    }
}
