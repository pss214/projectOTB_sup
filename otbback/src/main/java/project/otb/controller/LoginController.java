package project.otb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.otb.dto.BusDTO;
import project.otb.dto.LoginDto;
import project.otb.dto.ResponseDTO;
import project.otb.dto.UserDTO;
import project.otb.service.BusService;
import project.otb.service.UserService;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "로그인",description = "로그인을 위한 컨트롤러")
public class LoginController {
    private final UserService userService;
    private final BusService busService;

    public LoginController(UserService userService, BusService busService) {
        this.userService = userService;
        this.busService=busService;
    }

    @PostMapping("/signup")
    @Operation(summary = "유저 회원가입", description = "유저 회원가입 api입니다.")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "201", description = "회원가입 성공",content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "회원가입 실패",content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    })
    public ResponseEntity<?> createUser(@RequestBody UserDTO dto){
        try {
            userService.create(dto);
            return ResponseEntity.created(URI.create("/user/signup")).body(ResponseDTO.builder()
                    .status(HttpStatus.CREATED.value()).message("회원가입이 완료되었습니다").build());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(ResponseDTO.builder()
                    .status(HttpStatus.BAD_REQUEST.value()).message(e.getMessage()).build());
        }
    }
    @PostMapping("/signin")
    @Operation(summary = "유저 및 버스 회원 로그인",description = "통합 로그인 api입니다.")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "로그인 성공",content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "로그인 실패",content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    })
    public ResponseEntity<?> authenticator(@RequestBody LoginDto dto){
        UserDTO user = userService.login(dto);
        if(user!= null) {
            return ResponseEntity.ok().body(ResponseDTO.builder()
                    .status(HttpStatus.OK.value())
                    .message("로그인 성공")
                    .data(List.of(user))
                    .build());
        }else {
            BusDTO user2 = busService.getLogin(dto);
            if(user2!=null){
                return ResponseEntity.ok( ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("로그인 성공")
                        .data(List.of(user2))
                        .build());
            }
            else {
                return ResponseEntity.badRequest().body(ResponseDTO
                        .builder().status(HttpStatus.BAD_REQUEST.value()).message("아이디나 비밀번호를 다시 확인해주세요").build());
            }
        }
    }
    @PostMapping("/bussignup")
    @Operation(summary = "버스 회원가입",description = "버스 회원가입 api입니다.")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "201", description = "회원가입 성공",content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "회원가입 실패",content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    })
    public ResponseEntity<?> createBus(@RequestBody BusDTO dto){
        try {
            busService.create(dto);
            return ResponseEntity.created(URI.create("/bussignup")).body(ResponseDTO.builder()
                    .status(HttpStatus.CREATED.value()).message("회원가입이 완료되었습니다").build());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(ResponseDTO
                    .builder().status(HttpStatus.BAD_REQUEST.value()).message(e.getMessage()).build());
        }
    }


}
