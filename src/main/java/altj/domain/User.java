package altj.domain;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.domain.Auditable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * На данный момент - самый жирный класс, описывает сущность, хранящуюся в БД.
 * Идеология и аннотации этих сущностей наследуется из Hibernate и JavaEE и,
 * на мой взгляд, глубоко порочна - сущность не должна "знать" о хранении себя в БД
 * ничего, этими знаниями должен эксклюзивно обладать DAO.
 * 
 * Аннотация @Table описывает таблицу и схему - как раз то, о чем я написал выше.
 * 
 * Имплементацию Auditable объясню и задействую в следующей итерации,
 * скажу только, что она описывает, кем и когда создавался или изменялся объект.
 * 
 * UserDetails нужен для аутентификации и авторизации.
 */
@Entity
@Table(name = "users", schema = "users")
public class User implements Auditable<User, Long>, UserDetails {
    private static final long serialVersionUID = 1L;

    // @Id - первичный ключ
    @Id
    private Long id;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = true)
    private String password;

    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = false)
    /*
     * Тайное знание - без такого описания типа прочитать или записать поле
     * в таблицу не удастся, будет выкидываться весьма невразумительная ошибка,
     * еще один момент, информацию о котором должно знать DAO, но не класс-носитель
     * данных.
     */
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime created;

    @Column(nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime updated;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public User getCreatedBy() {
        return null;
    }

    @Override
    public void setCreatedBy(User createdBy) {
    }

    @Override
    public DateTime getCreatedDate() {
        return created;
    }

    @Override
    public void setCreatedDate(DateTime created) {
        this.created = created;
    }

    @Override
    public User getLastModifiedBy() {
        return null;
    }

    @Override
    public void setLastModifiedBy(User lastModifiedBy) {
    }

    @Override
    public DateTime getLastModifiedDate() {
        return updated;
    }

    @Override
    public void setLastModifiedDate(DateTime updated) {
        this.updated = updated;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
