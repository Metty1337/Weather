package metty1337.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import metty1337.dto.LocationDto;
import metty1337.dto.SearchFormDto;
import metty1337.entity.Location;
import metty1337.entity.User;
import metty1337.exception.ExceptionMessages;
import metty1337.exception.LocationAlreadyAddedException;
import metty1337.exception.UserDoesNotExistException;
import metty1337.exception.UserDoesNotAuthorizedException;
import metty1337.mapper.LocationMapper;
import metty1337.repository.LocationRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LocationService {

  private final LocationRepository locationRepository;
  private final OpenWeatherService openWeatherService;
  private final LocationMapper locationMapper;
  private final UserService userService;

  @Transactional(readOnly = true)
  public List<Location> findAllByUser(User user) {
    return locationRepository.findAllByUser(user);
  }

  public List<LocationDto> getLocations(SearchFormDto searchFormDto) {
    String name = searchFormDto.getName();
    return openWeatherService.getLocationsByName(name);
  }

  @Transactional
  public void addLocation(LocationDto locationDto, Long userId) {
    if (userId == null) {
      throw new UserDoesNotAuthorizedException(
          ExceptionMessages.USER_DOES_NOT_AUTHORIZED_EXCEPTION.getMessage());
    }

    Location location = locationMapper.dtoToLocation(locationDto);

    location.setUser(userService.findById(userId)
        .orElseThrow(() -> new UserDoesNotExistException(
            ExceptionMessages.USER_DOES_NOT_EXIST_EXCEPTION.getMessage())));

    try {
      locationRepository.save(location);
    } catch (DataIntegrityViolationException e) {
      throw new LocationAlreadyAddedException(
          ExceptionMessages.LOCATION_ALREADY_ADDED_EXCEPTION.getMessage(), e);
    }
  }
}
