package project.otb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.otb.DTO.LoginDto;
import project.otb.DTO.ResponseDTO;
import project.otb.DTO.UserDTO;
import project.otb.entity.User;
import project.otb.jwt.TokenProvider;
import project.otb.repositiry.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
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
                .Created_Date(LocalDateTime.now())
                .build();

        return userRepository.save(user);
    }
    public UserDTO login(final LoginDto dto) {
        User user = userRepository.findByUsername(dto.getUsername());
        try {
            if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
                String token = tokenProvider.createToken(String.format("%s:%s", user.getUsername(), "USER"));

                return UserDTO.builder()
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .token("otb " + token)
                        .build();
            } else {
                return null;
            }
        }catch (Exception e){
            e.fillInStackTrace();
        }
        return null;
    }
    public UserDTO getUserInfo(org.springframework.security.core.userdetails.User dto){
        User user = userRepository.findByUsername(dto.getUsername());
        return UserDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .MD(user.getModified_Date())
                .build();
    }
    public ResponseDTO putUserInfo(UserDTO dto){
        User user = userRepository.findByUsername(dto.getUsername());
        //부분수정 (email,password)
        return ResponseDTO.builder()
                .message("회원정보 수정완료")
                .data(List.of(user.getClass()))
                .build();
    }
    public ResponseDTO delUserInfo(org.springframework.security.core.userdetails.User dto){
        User user = userRepository.findByUsername(dto.getUsername());
        userRepository.delete(user);
        return ResponseDTO.builder()
                .message("회원 삭제 완료")
                .build();
    }
}