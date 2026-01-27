package metty1337.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "login")
@Getter
@Entity
@Table(
    name = "users",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "users_login_key",
            columnNames = {"login"})
    })
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
