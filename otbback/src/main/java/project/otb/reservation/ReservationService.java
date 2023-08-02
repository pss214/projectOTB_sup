package project.otb.reservation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import project.otb.DTO.ResponseDTO;
import project.otb.repositiry.ReservationRepository;
import project.otb.entity.Reservation;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation saveReservation(ReservationDTO dto,User user) {
        Reservation reservation = Reservation.builder()
                .depart_station(dto.getDepart_station())
                .busnumber(dto.getBusnumber())
                .BusNumberPlate(dto.getBusnumberplate())
                .arrive_station(dto.getArrive_station())
                .username(user.getUsername())
                .Payment(Boolean.parseBoolean(dto.getPayment()))
                .rtuinum(user.getUsername() + dto.getBusnumber() + LocalDateTime.now())
                .build();
            return reservationRepository.save(reservation);
        }
    public ReservationDTO getReservationInfo(User dto) {
        Reservation reservation = reservationRepository.findByUsername(dto.getUsername());
        if (reservation != null) {
            return ReservationDTO.builder()
                    .depart_station(reservation.getDepart_station())
                    .busnumber(reservation.getBusnumber())
                    .busnumberplate(reservation.getBusNumberPlate())
                    .arrive_station(reservation.getArrive_station())
                    .username(reservation.getUsername())
                    .payment(String.valueOf(reservation.isPayment()))
                    .rtuinum(reservation.getRtuinum())
                    .build();
        } else {
            throw new RuntimeException("예약정보 없음");
        }
    }
}
