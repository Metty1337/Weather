package metty1337.service.interfaces;

import java.util.List;
import metty1337.dto.LocationDto;
import metty1337.dto.SearchFormDto;
import metty1337.entity.Location;
import org.springframework.transaction.annotation.Transactional;

public interface LocationService {

  @Transactional(readOnly = true)
  List<Location> findAllByUser(Long userId);

  List<LocationDto> getLocations(SearchFormDto searchFormDto);

  @Transactional
  void addLocation(LocationDto locationDto, Long userId);

  @Transactional
  void deleteLocation(String latitude, String longitude, Long userId);
}
