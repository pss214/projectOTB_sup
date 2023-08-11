package project.otb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.otb.DTO.BusLiveByRouteDTO;
import project.otb.DTO.BusRouteNmDTO;
import project.otb.DTO.ReservationDTO;
import project.otb.entity.Reservation;
import project.otb.repositiry.ReservationRepository;

import java.util.List;

@Slf4j
@Service
public class BusDriverService {
    private final ReservationRepository reservationRepository;

    public BusDriverService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public BusLiveByRouteDTO getBusDriverInfo(BusLiveByRouteDTO dto) {
        List<Reservation> reservation = reservationRepository.findByBusNumberPlate(dto.getPlainNo1());
        if (reservation != null) {
            return null;
        }
        return null;
    }
}
