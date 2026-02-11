package metty1337.repository;

import java.math.BigDecimal;
import java.util.List;
import metty1337.entity.Location;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

  @EntityGraph(attributePaths = "user")
  List<Location> findAllByUserId(Long userId);

  void deleteByLatitudeAndLongitudeAndUserId(BigDecimal latitude, BigDecimal longitude,
      Long userId);
}

