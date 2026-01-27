package metty1337.repository;

import java.util.Optional;
import metty1337.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByLogin(String login);
}
