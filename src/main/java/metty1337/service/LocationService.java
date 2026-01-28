package metty1337.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import metty1337.entity.Location;
import metty1337.entity.User;
import metty1337.repository.LocationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {
  private final LocationRepository locationRepository;

  public List<Location> findAllByUser(User user) {
    return locationRepository.findAllByUser(user);
  }
}
