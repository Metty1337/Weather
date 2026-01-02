package metty1337.entity;

import jakarta.persistence.*;
import lombok.*;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "login")
@Getter
@Entity
@Table(name = "users", schema = "public", uniqueConstraints = {@UniqueConstraint(name = "users_login_key",
        columnNames = {"login"})})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "login", nullable = false, length = 20)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}