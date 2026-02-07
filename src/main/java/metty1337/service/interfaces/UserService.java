package metty1337.service.interfaces;

import java.util.Optional;
import metty1337.dto.SignUpFormDto;
import metty1337.entity.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

  @Transactional(readOnly = true)
  Optional<User> findByLogin(String login);

  @Transactional
  User createUser(SignUpFormDto signUpFormDto);

  @Transactional(readOnly = true)
  Optional<User> findById(long id);
}
