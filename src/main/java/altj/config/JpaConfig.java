package altj.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Пустой класс, в принципе, аннотацию @EnableJpaRepositories можно прицепить
 * к любой другой конфигурации, но мне показалось, что так понятнее.
 */
@Configuration
@EnableJpaRepositories(basePackages = "altj.dao")
public class JpaConfig {
}
