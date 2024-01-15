package project.otb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import project.otb.dto.ResponseDTO;
import project.otb.dto.UserDTO;
import project.otb.service.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/member")
@Tag(name = "유저",description = "유저페이지 컨트롤러")
@PreAuthorize("hasAnyRole('USER')")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    @Operation(summary = "유저의 마이페이지",description = "유저 마이페이지 API 입니다.")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "성공",content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "실패",content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    })
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal User user){
        try {
            System.out.println(user.getAuthorities());
            return ResponseEntity.ok().body(ResponseDTO.builder()
                    .status(HttpStatus.OK.value()).message(user.getUsername()+"의 마이페이지").data(List.of(userService.getUserInfo(user))).build());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDTO.builder()
                    .status(HttpStatus.BAD_REQUEST.value()).message(e.getMessage()).build());
        }
    }
    @PostMapping
    @Operation(summary = "유저 정보 수정",description = "유저 정보를 수정하는 API 입니다.")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "201", description = "성공",content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "실패",content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    })
    public ResponseEntity<?> putUserInfo(@AuthenticationPrincipal User user,@RequestBody UserDTO dto){
        try{
            if (dto.getEmail() != "") {
            } else {
                dto.setEmail(null);
            }
            if (dto.getPassword() != ""){
            }else {
                dto.setPassword(null);
            }
            userService.putUserInfo(user,dto);
            return ResponseEntity.created(URI.create("/member")).body(ResponseDTO.builder()
                    .status(HttpStatus.CREATED.value()).message("회원 정보 수정 완료").build());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(ResponseDTO.builder()
                    .status(HttpStatus.BAD_REQUEST.value()).message(e.getMessage()).build());
        }
    }
    @DeleteMapping
    @Operation(summary = "유저 탈퇴",description = "유저가 탈퇴를 하면 유저 정보를 지우는 API 입니다.")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "201", description = "성공",content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "실패",content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    })
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
