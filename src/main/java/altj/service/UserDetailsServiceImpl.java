package altj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import altj.dao.UserRepository;
import altj.domain.User;

/**
 * Этот тривиальный класс нужен для авторизации и аутентификации.
 * 
 * Аннотация @Service позволяет обнаружить его Spring'у и
 * создать синглтон в момент, когда этот объект потребуется 
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;

    /**
     * Аннотация @Autowired перед конструктором говорит о том,
     * что параметры в него будут переданы Spring'ом в контексте
     * магии внедрения зависимостей. 
     */
    @Autowired
    private UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
}
