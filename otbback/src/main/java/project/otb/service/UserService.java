package project.otb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.otb.dto.LoginDto;
import project.otb.dto.UserDTO;
import project.otb.entity.User;
import project.otb.repository.BusRepository;
import project.otb.security.TokenProvider;
import project.otb.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
public class UserService {
        private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final BusRepository busRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider, BusRepository busRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.busRepository = busRepository;
    }

    public User create(final UserDTO dto){
        if(userRepository.existsByUsername(dto.getUsername())&&busRepository.existsBybusNumberPlate(dto.getUsername())&& Objects.equals(dto.getUsername(), "admin")){
            throw new RuntimeException("아이디가 존재합니다!");
        }
        else{
                User user= User.builder()
                        .password(passwordEncoder.encode(dto.getPassword()))
                        .username(dto.getUsername())
                        .email(dto.getEmail())
                        .Created_Date(LocalDateTime.now())
                        .build();
                return userRepository.save(user);
            }
    }
    public UserDTO login(final LoginDto dto) {
        User user = userRepository.findByUsername(dto.getUsername());
        if(user!=null&&passwordEncoder.matches(dto.getPassword(), user.getPassword())){
            String token = tokenProvider.createToken(String.format("%s:%s", user.getUsername(), "ROLE_USER"));

            return UserDTO.builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .token(token)
                    .type("user")
                    .build();
        } else {
            return null;
        }
    }
    public UserDTO getUserInfo(org.springframework.security.core.userdetails.User dto){
        User user = userRepository.findByUsername(dto.getUsername());
        if(user!=null){
            return UserDTO.builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .CD(user.getCreated_Date())
                    .MD(user.getModified_Date())
                    .build();
        }
        else{
            throw new RuntimeException("회원 정보 없음");
        }
    }
    public void putUserInfo(org.springframework.security.core.userdetails.User user, UserDTO dto){
        User saveuser = userRepository.findByUsername(user.getUsername());
        if(dto.getEmail() != null){
            saveuser.updateUser(dto.getEmail(), saveuser.getPassword());
            userRepository.save(saveuser);
        }
        if(dto.getPassword()!=null){
            saveuser.updateUser(saveuser.getEmail(), passwordEncoder.encode(dto.getPassword()));
            userRepository.save(saveuser);
        }

    }
    public void delUserInfo(org.springframework.security.core.userdetails.User dto){
        User user = userRepository.findByUsername(dto.getUsername());
        if(user!=null){
            userRepository.delete(user);
        }else {
            throw new RuntimeException("회원 정보 없음");
        }

    }
}