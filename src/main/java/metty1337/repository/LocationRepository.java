package metty1337.repository;

import java.util.List;
import metty1337.entity.Location;
import metty1337.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

  Location findByUser(User user);

  List<Location> findAllByUser(User user);
}
