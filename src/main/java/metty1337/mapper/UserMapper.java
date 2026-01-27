package metty1337.mapper;

import metty1337.dto.UserDto;
import metty1337.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "username", source = "login")
  UserDto entityToDto(User user);
}
