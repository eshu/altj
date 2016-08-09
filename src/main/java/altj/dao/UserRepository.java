package altj.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import altj.domain.User;

/**
 * Не нужно искать имплементацию этого интерфейса - она генерируется
 * при запуске приложения. Запросы для извлечения данных генерируются
 * по названию методов.
 * 
 * Подробности тут:
 * http://docs.spring.io/spring-data/jpa/docs/1.10.2.RELEASE/reference/html/#repositories.query-methods.query-creation
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}
