package metty1337.mapper;

import metty1337.dto.LocationDto;
import metty1337.entity.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
  Location dtoToLocation(LocationDto locationDto);
}
