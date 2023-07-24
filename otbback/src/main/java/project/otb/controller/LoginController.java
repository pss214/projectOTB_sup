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
            return ResponseEntity.ok().body(userService.create(dto));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(ResponseDTO.builder().message(e.getMessage()).build());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticator(@RequestBody LoginDto dto){
        UserDTO user = userService.login(dto);
        if(user!= null) {
            return ResponseEntity.ok().body(ResponseDTO.builder()
                    .message("로그인 성공")
                    .data(List.of(user))
                    .build());
        }else {
            BusDTO user2 = busService.getLogin(dto);
            if(user2!=null){
                return ResponseEntity.ok( ResponseDTO.builder()
                        .message("로그인 성공")
                        .data(List.of(user2))
                        .build());
            }
            else {
                return ResponseEntity.badRequest().body(ResponseDTO
                        .builder().message("아이디나 비밀번호를 다시 확인해주세요").build());
            }
        }
    }
    @PostMapping("/bussignup")
    public ResponseEntity<?> createBus(@RequestBody BusDTO dto){
        try {
            return ResponseEntity.ok().body(busService.create(dto));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(ResponseDTO
                    .builder().message(e.getMessage()).build());
        }
    }


}
