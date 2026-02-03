package metty1337.repository;

import java.math.BigDecimal;
import java.util.List;
import metty1337.entity.Location;
import metty1337.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

  List<Location> findAllByUser(User user);

  List<Location> user(User user);

  void deleteByLatitudeAndLongitudeAndUserId(BigDecimal latitude, BigDecimal longitude, Long userId);
}

