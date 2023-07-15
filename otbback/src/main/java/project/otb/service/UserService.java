package project.otb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.otb.DTO.LoginDto;
import project.otb.DTO.UserDTO;
import project.otb.entity.User;
import project.otb.repositiry.UserRepository;

import java.time.LocalDateTime;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(final UserDTO entity){
        final String username = entity.getUsername();
        if(userRepository.existsByUsername(username)){
            log.warn("username Already Exists!, {}", username);
            throw new RuntimeException("아이디가 존재합니다!");
        }
        User user= User.builder()
                .password(passwordEncoder.encode(entity.getPassword()))
                .username(entity.getUsername())
                .email(entity.getEmail())
                .nickname(entity.getNickname())
                .Created_Date(LocalDateTime.now())
                .build();

        return userRepository.save(user);
    }
    public UserDTO getByCredentials(final LoginDto dto) {
        User user = userRepository.findByUsername(dto.getUsername());
        if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
//            final String token = tokenProvider.create(user);

            final UserDTO response = UserDTO.builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .nickname(user.getNickname())
//                    .token(token)
                    .build();
//            log.info(tokenProvider.validateAndGetUserId(token));
            return response;
        }else {
            return null;
        }

    }
}