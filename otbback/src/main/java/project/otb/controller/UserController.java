package project.otb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import project.otb.DTO.UserDTO;
import project.otb.service.UserService;

@RestController
@RequestMapping("/member")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UserDTO getUserInfo(@AuthenticationPrincipal User user){
        return userService.getUserInfo(user);
    }
    @PutMapping
    public ResponseEntity<?> putUserInfo(@RequestBody UserDTO user){ return ResponseEntity.ok().body(userService.putUserInfo(user));}
    @DeleteMapping
    public ResponseEntity<?> delUserInfo(@AuthenticationPrincipal User user){ return ResponseEntity.ok().body(userService.delUserInfo(user));}
}
