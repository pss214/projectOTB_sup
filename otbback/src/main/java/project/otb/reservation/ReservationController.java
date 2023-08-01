package project.otb.reservation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import project.otb.DTO.ResponseDTO;
import project.otb.entity.Reservation;
import project.otb.repositiry.ReservationRepository;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {this.reservationService = reservationService;}

    @GetMapping
    public ResponseEntity<?> getInfo(@AuthenticationPrincipal User user) {
        try {
            return ResponseEntity.ok().body(ResponseDTO.builder()
                    .status(HttpStatus.CREATED.value()).message(user.getUsername()).data(List.of(reservationService.getReservationInfo(user))).build());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDTO.builder()
                    .status(HttpStatus.BAD_REQUEST.value()).message(e.getMessage()).build());
        }
    }
    @PostMapping
    public ResponseEntity<?> saveReservation(@RequestBody ReservationDTO dto, @AuthenticationPrincipal User user) {
        try {
            Reservation savedReservation = reservationService.saveReservation(dto, user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseDTO.builder()
                            .status(HttpStatus.CREATED.value())
                            .message("예약 정보가 저장되었습니다.")
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
}
