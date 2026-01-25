package metty1337.service;

import lombok.RequiredArgsConstructor;
import metty1337.dto.SignUpFormDto;
import metty1337.entity.User;
import metty1337.exception.ExceptionMessages;
import metty1337.exception.UserAlreadyExistException;
import metty1337.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional(readOnly = true)
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Transactional
    public User createUser(SignUpFormDto signUpFormDto) {
        try {
            return userRepository.save(new User(signUpFormDto.getUsername(), passwordEncoder.encode(signUpFormDto.getPassword())));
        } catch (DataIntegrityViolationException e) {
            log.warn("User with username={} already exist", signUpFormDto.getUsername());
            throw new UserAlreadyExistException(ExceptionMessages.USER_ALREADY_EXIST_EXCEPTION.getMessage(), e);
        }
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }
}
