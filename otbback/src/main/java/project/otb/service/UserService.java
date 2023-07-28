package project.otb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.otb.DTO.LoginDto;
import project.otb.DTO.ResponseDTO;
import project.otb.DTO.UserDTO;
import project.otb.entity.User;
import project.otb.repositiry.BusRepository;
import project.otb.security.TokenProvider;
import project.otb.repositiry.UserRepository;

import java.time.LocalDateTime;

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
        if(userRepository.existsByUsername(dto.getUsername())){
            throw new RuntimeException("아이디가 존재합니다!");
        }
        else{
            if(busRepository.existsBybusNumberPlate(dto.getUsername())){
                throw new RuntimeException("아이디가 존재합니다!");
            }else {
                User user= User.builder()
                        .password(passwordEncoder.encode(dto.getPassword()))
                        .username(dto.getUsername())
                        .email(dto.getEmail())
                        .Created_Date(LocalDateTime.now())
                        .build();
                return userRepository.save(user);
            }
        }
    }
    public UserDTO login(final LoginDto dto) {
        User user = userRepository.findByUsername(dto.getUsername());
        if(user==null){
            return null;
        }
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
    public UserDTO putUserInfo(org.springframework.security.core.userdetails.User user, UserDTO dto){
        User saveuser = userRepository.findByUsername(user.getUsername());
        if(user!=null){
            if(dto.getEmail()!=null){
                saveuser.setEmail(dto.getEmail());
            }
            if(dto.getPassword()!=null){
                saveuser.setPassword(passwordEncoder.encode(dto.getPassword()));
            }
            return null;
        }else {
            throw new RuntimeException("회원 정보 없음");
        }

    }
    public UserDTO delUserInfo(org.springframework.security.core.userdetails.User dto){
        User user = userRepository.findByUsername(dto.getUsername());
        if(user!=null){
            userRepository.delete(user);
            return null;
        }else {
            throw new RuntimeException("회원 정보 없음");
        }

    }
}