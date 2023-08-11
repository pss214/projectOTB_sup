package project.otb.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.otb.DTO.ResponseDTO;
import project.otb.api.BusApiService;

import java.util.List;

@RestController
@RequestMapping("/bus-driver")
@Tag(name = "BUS",description = "버스기사 컨트롤러")
public class BusDriverController {
    private BusApiService busApiService;

    @PostMapping("/get-onoff")
    public ResponseEntity<?> BusLiveGettingOnOff(@AuthenticationPrincipal User user){

        return null;
    }
    public ResponseEntity<?> BusDriverCreate(@AuthenticationPrincipal User user){
        return ResponseEntity.ok().body(ResponseDTO.builder()
                .status(HttpStatus.OK.value()).message("전송되었습니다.")
                .data(List.of(busApiService.GetBusLiveByRoute(user.getUsername())))
                .build());
    }
}
