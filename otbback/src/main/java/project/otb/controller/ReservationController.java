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
import org.springframework.web.bind.annotation.*;
import project.otb.dto.ReservationDTO;
import project.otb.dto.ResponseDTO;
import project.otb.service.BusApiService;
import project.otb.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@Tag(name = "예약",description = "예약을 위한 컨트롤러")
public class ReservationController {
    private final ReservationService reservationService;
    private final BusApiService busApiService;

    public ReservationController(ReservationService reservationService, BusApiService busApiService) {this.reservationService = reservationService;
        this.busApiService = busApiService;
    }

    @GetMapping
    @Operation(summary = "예약 내역 조회",description = "유저가 예약한 정보를 리스트형태로 보내는 API 입니다.")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "성공",content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "실패",content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    })
    public ResponseEntity<?> getInfo(@AuthenticationPrincipal User user) {
        try {
            return ResponseEntity.ok().body(ResponseDTO.builder()
                    .status(HttpStatus.CREATED.value()).message(user.getUsername())
                    .data(List.of(reservationService.getReservationInfo(user))).build());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDTO.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage()).data(List.of(e)).build());
        }
    }
    @PostMapping
    @Operation(summary = "예약 내역 저장",description = "유저가 예약한 내용을 저장하는 API 입니다.")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "201", description = "성공",content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "실패",content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    })
    public ResponseEntity<?> saveReservation(@RequestBody ReservationDTO dto, @AuthenticationPrincipal User user) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseDTO.builder()
                            .status(HttpStatus.CREATED.value())
                            .message("예약 정보가 저장되었습니다.")
                            .data(List.of(reservationService.saveReservation(dto, user).getRtuinum()))
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDTO.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .data(List.of(e))
                            .message("예약 정보 저장 중 오류가 발생하였습니다.")
                            .build());
        }
    }

    @PostMapping("/pay")
    @Operation(summary = "결제 유무",description = "유저가 예약 한 후 선결제한 것을 저장하는 api 입니다.")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "201", description = "성공",content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "실패",content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    })
    public ResponseEntity<?> paymentReservation(@RequestBody ReservationDTO dto, @AuthenticationPrincipal User user){
        try {
            reservationService.putReservationPay(user,dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseDTO.builder()
                            .status(HttpStatus.CREATED.value())
                            .message("결제 정보가 저장되었습니다.")
                            .data(List.of())
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDTO.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .data(List.of(e))
                            .message("결제 중 오류가 발생하였습니다.")
                            .build());
        }
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "예약 삭제",description = "유저가 예약 정보를 삭제하는 api 입니다.")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "201", description = "성공",content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "실패",content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    })
    public ResponseEntity<?> delReservationInfo(@PathVariable String id) {
        try {
            reservationService.delReservation(id);
            return ResponseEntity.ok().body(ResponseDTO.builder()
                    .status(HttpStatus.CREATED.value()).message("예약 정보 삭제 완료").build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDTO.builder()
                    .status(HttpStatus.BAD_REQUEST.value()).message(e.getMessage()).build());
        }
    }
}
