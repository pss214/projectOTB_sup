package project.otb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.reactive.function.client.WebClient;
import project.otb.dto.kakao.PayApproveDTO;
import project.otb.dto.kakao.PayApproveResDTO;
import project.otb.dto.kakao.PayReadyResDTO;
import project.otb.dto.kakao.PayReadyDTO;
import project.otb.dto.ResponseDTO;
import project.otb.service.PayService;

import java.io.EOFException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/pay")
@PreAuthorize("permitAll()")
@RequiredArgsConstructor
public class PayController {
    private final PayService payService;

    @GetMapping
    public ResponseEntity<?> PayReady(@AuthenticationPrincipal User user){
        if(Objects.equals(user.getUsername(), "anonymous"))
            return ResponseEntity.badRequest().body("잘못된 요청입니다.");
        return ResponseEntity.ok().body(ResponseDTO.builder()
                .status(HttpStatus.OK.value())
                .data(List.of(payService.PayReady(user.getUsername())))
                .build());
    }
    @GetMapping("/success")
    public ResponseEntity<?> PaySuccess(@RequestParam("pg_token") String pgToken){
        return ResponseEntity.ok().body(ResponseDTO.builder()
                .status(HttpStatus.OK.value())
                .message("결제를 완료했습니다!")
                .data(List.of(payService.PayApprove(pgToken)))
                .build());
    }
    @GetMapping("/cancel")
    public ResponseEntity<?> PayCancel(){
        return ResponseEntity.badRequest().body("결제를 취소했습니다!");
    }
    @GetMapping("/fail")
    public ResponseEntity<?> PayFail(){
        return ResponseEntity.badRequest().body("결제를 실패했습니다!");
    }
}
