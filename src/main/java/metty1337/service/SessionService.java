package metty1337.service;

import lombok.RequiredArgsConstructor;
import metty1337.entity.Session;
import metty1337.repository.SessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;

    @Transactional(readOnly = true)
    public Optional<Session> findByToken(String token) {
        UUID sessionId = UUID.fromString(token);
        return sessionRepository.findById(sessionId);
    }

    @Transactional
    public void createSession(Session session) {
        sessionRepository.save(session);
    }

    @Transactional
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
