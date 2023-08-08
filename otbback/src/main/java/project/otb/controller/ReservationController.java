package project.otb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import project.otb.DTO.ReservationDTO;
import project.otb.DTO.ResponseDTO;
import project.otb.DTO.StationDTO;
import project.otb.api.BusApiService;
import project.otb.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;
    private final BusApiService busApiService;

    public ReservationController(ReservationService reservationService, BusApiService busApiService) {this.reservationService = reservationService;
        this.busApiService = busApiService;
    }

    @GetMapping
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
    public ResponseEntity<?> saveReservation(@RequestBody ReservationDTO dto, @AuthenticationPrincipal User user) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseDTO.builder()
                            .status(HttpStatus.CREATED.value())
                            .message("예약 정보가 저장되었습니다.")
                            .data(List.of(reservationService.saveReservation(dto, user)))
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
    @PostMapping("/businfo")
    public ResponseEntity<?> Businpormation(@RequestBody StationDTO dto){
        return ResponseEntity.ok().body(ResponseDTO.builder()
                .status(HttpStatus.OK.value()).message("완료되었습니다")
                .data(busApiService.GetBusStation(dto.getId())).build());
    }
    @PostMapping("/busroutenm")
    public ResponseEntity<?> Busroutenm(@RequestBody StationDTO dto){
        return ResponseEntity.ok().body(ResponseDTO.builder()
                .status(HttpStatus.OK.value()).message("완료되었습니다")
                .data(busApiService.GetBusStationRoute(dto.getId())).build());
    }
}
