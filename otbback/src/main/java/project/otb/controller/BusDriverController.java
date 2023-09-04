package project.otb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.otb.DTO.ReservationDTO;
import project.otb.DTO.ResponseDTO;
import project.otb.service.BusService;

import java.util.List;

@RestController
@RequestMapping("/bus-driver")
@Tag(name = "BUS",description = "버스기사 컨트롤러")
public class BusDriverController {
    final private BusService busService;

    public BusDriverController(BusService busService) {
        this.busService = busService;
    }

    @PostMapping("/get-inout")
    @Operation(summary = "버스 승하차 알림",description = "유저의 예약정보를 받아 버스기사에게 승하차 여부를 알려주는 api 입니다.")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "성공",content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "실패",content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    })
    public ResponseEntity<?> BusLiveGettingOnOff(@AuthenticationPrincipal User user){
        try {
            return ResponseEntity.ok().body(ResponseDTO.builder()
                    .status(HttpStatus.CREATED.value()).data(List.of(busService.LiveStationInAndOut(user))).message("전송 완료").build());
        }catch (Exception e){
            return ResponseEntity.ok().body(ResponseDTO.builder()
                    .status(HttpStatus.BAD_REQUEST.value()).message(e.getMessage()).build());
        }
    }
    @PostMapping("/scan")
    @Operation(summary = "버스 예약정보 확인",description = "유저가 예약QR을 스켄하고 예약정보를 확인하는 API 입니다")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "201", description = "성공",content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "실패",content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    })
    public ResponseEntity<?> BusQRScan(@AuthenticationPrincipal User user, @RequestBody ReservationDTO dto){
        try {
            return ResponseEntity.ok().body(ResponseDTO.builder()
                    .status(HttpStatus.CREATED.value()).data(List.of(busService.QRScan(dto))).message("스켄 확인").build());
        }catch (Exception e){
            return ResponseEntity.ok().body(ResponseDTO.builder()
                    .status(HttpStatus.BAD_REQUEST.value()).message(e.getMessage()).build());
        }
    }
}
