package metty1337.job;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import metty1337.service.interfaces.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SessionCleanupJob {

  private static final Logger log = LoggerFactory.getLogger(SessionCleanupJob.class);
  private final SessionService sessionService;

  @Scheduled(cron = "0 */5 * * * *")
  @Transactional
  public void deleteExpiredSessions() {
    log.debug("Attempting to delete expired sessions");
    Instant now = Instant.now();
    long deleted = sessionService.deleteAllByExpiresAtBefore(now);

    log.info("Sessions deleted={}", deleted);
  }
}
