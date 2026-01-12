package metty1337.repository;

import metty1337.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {
    long deleteAllByExpiresAtBefore(Instant now);
}
