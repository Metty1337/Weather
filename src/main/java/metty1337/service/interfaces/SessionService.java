package metty1337.service.interfaces;

import java.time.Instant;
import java.util.Optional;
import metty1337.entity.Session;
import org.springframework.transaction.annotation.Transactional;

public interface SessionService {

  @Transactional(readOnly = true)
  Optional<Session> findByToken(String token);

  @Transactional
  void createSession(Session session);

  @Transactional
  void logout(String token);

  @Transactional
  long deleteAllByExpiresAtBefore(Instant now);
}
