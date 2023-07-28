package project.otb.reservation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import project.otb.DTO.ResponseDTO;
import project.otb.repositiry.ReservationRepository;
import project.otb.entity.Reservation;

import java.util.List;

@Slf4j
@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ResponseDTO responseDTO;

    public ReservationService(ReservationRepository reservationRepository, ResponseDTO responseDTO) {
        this.reservationRepository = reservationRepository;
        this.responseDTO = responseDTO;
    }
    public List<ReservationDTO> getReservationsByUsername(String username) {
        List<Reservation> reservations = (List<Reservation>) reservationRepository.findByUsername(username);
        return convertToDTOList(reservations);

        public ResponseDTO saveReservation(final ReservationDTO dto) {
            Reservation reservation = reservationRepository.findByUsername(dto.getUsername());
            if (reservation != null)
                return (List<ReservationDTO>) ResponseDTO.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("예약이 성공적으로 저장되었습니다.")
                        .data(null)
                        .build();
        }
    }
    private List<ReservationDTO> convertToDTOList(List<Reservation> reservations) {
        return null;
    }

    public Reservation saveReservation(Reservation reservation) {
        return reservation;
    }
}
