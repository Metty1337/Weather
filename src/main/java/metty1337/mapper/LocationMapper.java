package metty1337.mapper;

import metty1337.dto.LocationDto;
import metty1337.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationMapper {

  @Mapping(target = "user", ignore = true)
  Location dtoToLocation(LocationDto locationDto);
}
