package altj.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Конфигурация безопасности, параметры читаются из config/application.yaml 
 */
@Configuration
@EnableWebSecurity
@ConfigurationProperties(prefix = "altj.security", ignoreUnknownFields = false)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private String rememberMeKey;
    private int rememberMeTokenValiditySeconds = Integer.MAX_VALUE;
    private int maximumSessions = 1;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Описание сессий, метода входа в систему, защищенные контроллеры и т.д.
     * Практически конфигурирует объект HttpSecurity
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .sessionManagement()
                .maximumSessions(maximumSessions)
                .maxSessionsPreventsLogin(false)
                .sessionRegistry(sessionRegistry());
        http
            .authorizeRequests()
                .antMatchers("/greeting").authenticated()
                .anyRequest().permitAll();
        http
            .formLogin().loginPage("/login").permitAll()
            .and()
            .logout().logoutSuccessUrl("/").permitAll()
            .and()
            .rememberMe()
                .key(rememberMeKey)
                .tokenValiditySeconds(rememberMeTokenValiditySeconds)
                .userDetailsService(userDetailsService);
    }

    /**
     * Инициализация синглтона фабричным методом.
     * Данный метод вызовется автоматически один раз, когда потребуется экземпляр.
     */
    @Bean
    public SessionRegistry sessionRegistry() {
        logger.trace("Session registry created");
        return new SessionRegistryImpl();
    }

    @Autowired
    protected void configureGlobal(
            AuthenticationManagerBuilder auth,
            AuthenticationProvider authenticationProvider) throws Exception {
        auth.authenticationProvider(authenticationProvider);
        auth.eraseCredentials(true);
    }

    @Bean
    @Autowired
    public AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void setRememberMeKey(String rememberMeKey) {
        this.rememberMeKey = rememberMeKey;
    }

    public void setMaximumSessions(int maximumSessions) {
        this.maximumSessions = maximumSessions;
    }

    public void setTokenValiditySeconds(int rememberMeTokenValiditySeconds) {
        this.rememberMeTokenValiditySeconds = rememberMeTokenValiditySeconds;
    }
}
