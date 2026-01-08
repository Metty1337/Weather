package metty1337.service;

import lombok.RequiredArgsConstructor;
import metty1337.dto.SignUpFormDto;
import metty1337.entity.User;
import metty1337.mapper.UserMapper;
import metty1337.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(SignUpFormDto signUpFormDto) {
        userRepository.save(
                new User(signUpFormDto.getUsername(),
                        passwordEncoder.encode(signUpFormDto.getPassword()))
        );
    }

    @Transactional(readOnly = true)
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
