package project.otb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import project.otb.DTO.LoginDto;
import project.otb.DTO.ResponseDTO;
import project.otb.DTO.UserDTO;
import project.otb.entity.User;
import project.otb.security.TokenProvider;
import project.otb.service.UserService;
/*

ex)
http://localhost:8080/api/sginup
in data
{
    "username":"pss",
    "password":"password",
    "nickname":"pss",
    "email":"username@gmail.com"
}
out data
{
    "username": "pss",
    "password": "password",
    "nickname": "pss",
    "email": "username@gmail.com",
    "token": null,
    "cd": "2023-07-15T18:13:38.8908674"//회원가입 후 생성시각
}
http://localhost:8080/api/login
{
    "username":"pss",
    "password":"password"
}
 */

@Slf4j
@RestController
@RequestMapping("/api")
public class LoginController {
    private final UserService userService;
    private final TokenProvider tokenProvider;

    public LoginController(UserService userService, TokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody UserDTO dto){
        try {
            User createdUser = userService.create(dto);
            UserDTO responseUserDTO = UserDTO.builder()
                    .username(createdUser.getUsername())
                    .password(createdUser.getPassword())
                    .nickname(dto.getNickname())
                    .email(createdUser.getEmail())
                    .CD(createdUser.getCreated_Date())
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        }catch (Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticator(@RequestBody LoginDto dto){
        UserDTO user = userService.getByCredentials(dto);
        if(user!= null) {
            return ResponseEntity.ok().body(user);
        }else {
            ResponseDTO response = ResponseDTO.builder().error("아이디나 비밀번호를 다시 확인해주세요").build();
            return ResponseEntity.badRequest().body(response);
        }
    }

}
