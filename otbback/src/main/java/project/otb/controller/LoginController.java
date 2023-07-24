package project.otb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.otb.DTO.BusDTO;
import project.otb.DTO.LoginDto;
import project.otb.DTO.ResponseDTO;
import project.otb.DTO.UserDTO;
import project.otb.entity.Bus;
import project.otb.entity.User;
import project.otb.service.BusService;
import project.otb.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
/*

ex)
http://localhost:8080/api/signup
in data
{
    "username":"pss",
    "password":"password",
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
@RequestMapping("/user")
public class LoginController {
    private final UserService userService;
    private final BusService busService;

    public LoginController(UserService userService, BusService busService) {
        this.userService = userService;
        this.busService=busService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody UserDTO dto){
        try {
            User createdUser = userService.create(dto);
            UserDTO responseUserDTO = UserDTO.builder()
                    .username(createdUser.getUsername())
                    .password(createdUser.getPassword())
                    .email(createdUser.getEmail())
                    .CD(createdUser.getCreated_Date())
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        }catch (Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder().message(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticator(@RequestBody LoginDto dto){
        UserDTO user = userService.login(dto);
        if(user!= null) {
            return ResponseEntity.ok().body(user);
        }else {
            BusDTO user2 = busService.getLogin(dto);
            if(user2!=null){
                return ResponseEntity.ok( ResponseDTO.builder()
                        .message("로그인 성공")
                        .data(List.of(user2))
                        .build());
            }
            else {
                ResponseDTO response = ResponseDTO.builder().message("아이디나 비밀번호를 다시 확인해주세요").build();
                return ResponseEntity.badRequest().body(response);
            }
        }
    }
    @PostMapping("/bussignup")
    public ResponseEntity<?> createBus(@RequestBody BusDTO dto){
        try {
            ResponseDTO createdBus = busService.create(dto);
            return ResponseEntity.ok().body(createdBus);
        }catch (Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder().message(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }


}
