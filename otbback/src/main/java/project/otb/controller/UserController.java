package project.otb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import project.otb.DTO.ResponseDTO;
import project.otb.DTO.UserDTO;
import project.otb.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/member")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseDTO getUserInfo(@AuthenticationPrincipal User user){
        return ResponseDTO.builder()
                .message(user.getUsername()+"의 마이페이지")
                .data(List.of(userService.getUserInfo(user)))
                .build();
    }
    @PostMapping
    public ResponseEntity<?> putUserInfo(@AuthenticationPrincipal User user,@RequestBody UserDTO dto){ return ResponseEntity.ok().body(userService.putUserInfo(user,dto));}
    @DeleteMapping
    public ResponseEntity<?> delUserInfo(@AuthenticationPrincipal User user){ return ResponseEntity.ok().body(userService.delUserInfo(user));}
}
