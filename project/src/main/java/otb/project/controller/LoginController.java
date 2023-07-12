package otb.project.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import otb.project.entity.User;
import otb.project.repositiry.UserRepository;
import otb.project.security.ResponseDTO;
import otb.project.security.TokenProvider;
import otb.project.security.UserDTO;
import otb.project.service.UserService;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenProvider tokenProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/sign")
    public ResponseEntity<?> createUser(@RequestBody UserDTO dto){
        try {
            User user= User.builder()
                    .password(dto.getPassword())
                    .username(dto.getUsername())
                    .email(dto.getEmail())
                    .nickname(dto.getNickname())
                    .Created_Date(LocalDateTime.now())
                    .build();

            User createdUser = userService.create(user);
            UserDTO responseUserDTO = UserDTO.builder()
                    .username(createdUser.getUsername())
                    .id(createdUser.getId())
                    .password(createdUser.getPassword())
                    .email(createdUser.getEmail())
                    .CD(createdUser.getCreated_Date())
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        }catch (Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticator(@RequestBody UserDTO dto){
        User user =userService.getByCredentials(dto.getUsername(), dto.getPassword());
        if(user != null){
            final String token = tokenProvider.create(user);

            final UserDTO response = UserDTO.builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .id(user.getId())
                    .token(token)
                    .build();
            log.info(tokenProvider.validateAndGetUserId(token));
            return ResponseEntity.ok().body(response);
        }else {
            ResponseDTO response = ResponseDTO.builder().error("Login Failed").build();
            return ResponseEntity.badRequest().body(response);
        }
    }

}
