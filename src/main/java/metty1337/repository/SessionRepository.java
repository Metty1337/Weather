package metty1337.repository;

import java.time.Instant;
import java.util.UUID;
import metty1337.entity.Session;
import metty1337.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, UUID> {

  long deleteAllByExpiresAtBefore(Instant now);

  void deleteAllByUser(User user);
}
