package project.otb.controller;

import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal User user){
        try {
            return ResponseEntity.ok().body(ResponseDTO.builder()
                    .status(HttpStatus.OK.value()).message(user.getUsername()+"의 마이페이지").data(List.of(userService.getUserInfo(user))).build());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDTO.builder()
                    .status(HttpStatus.BAD_REQUEST.value()).message(e.getMessage()).build());
        }
    }
    @PostMapping
    public ResponseEntity<?> putUserInfo(@AuthenticationPrincipal User user,@RequestBody UserDTO dto){
        try{
            userService.putUserInfo(user,dto);
            return ResponseEntity.ok().body(ResponseDTO.builder()
                    .status(HttpStatus.CREATED.value()).message("회원 정보 수정 완료").build());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(ResponseDTO.builder()
                    .status(HttpStatus.BAD_REQUEST.value()).message(e.getMessage()).build());
        }
    }
    @DeleteMapping
    public ResponseEntity<?> delUserInfo(@AuthenticationPrincipal User user){
        try {
            userService.delUserInfo(user);
            return ResponseEntity.ok().body(ResponseDTO.builder()
                    .status(HttpStatus.CREATED.value()).message("회원 정보 삭제 완료").build());}
        catch (Exception e){
            return ResponseEntity.badRequest().body(ResponseDTO.builder()
                    .status(HttpStatus.BAD_REQUEST.value()).message(e.getMessage()).build());
        }
        }

}
