package metty1337.repository;

import metty1337.entity.Session;
import metty1337.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {
    Session findByUser(User user);
}
