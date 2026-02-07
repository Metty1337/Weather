package metty1337.service.interfaces;

import metty1337.dto.SignInFormDto;
import org.springframework.transaction.annotation.Transactional;

public interface AuthService {

  @Transactional
  String authenticate(SignInFormDto signInFormDto);
}
