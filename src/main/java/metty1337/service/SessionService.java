package metty1337.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import metty1337.entity.Session;
import metty1337.repository.SessionRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "sessions")
public class SessionService {

  private final SessionRepository sessionRepository;

  @Transactional(readOnly = true)
  @Cacheable(key = "#token")
  public Optional<Session> findByToken(String token) {
    try {
      return sessionRepository.findById(UUID.fromString(token));
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }

  @Transactional
  @CachePut(key = "#session.id.toString()")
  public Session createSession(Session session) {
    sessionRepository.deleteAllByUser(session.getUser());
    return sessionRepository.save(session);
  }

  @Transactional
  @CacheEvict(key = "#token")
  public void logout(String token) {
    if (token == null || token.isBlank()) {
      return;
    }
    sessionRepository.deleteById(UUID.fromString(token));
  }

  @Transactional
  public long deleteAllByExpiresAtBefore(Instant now) {
    return sessionRepository.deleteAllByExpiresAtBefore(now);
  }
}
