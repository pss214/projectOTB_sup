package project.otb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.otb.DTO.ReservationDTO;
import project.otb.api.BusApiService;
import project.otb.entity.Bus;
import project.otb.repositiry.BusRepository;
import project.otb.repositiry.ReservationRepository;
import project.otb.entity.Reservation;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final BusApiService busApiService;
    private final BusRepository busRepository;
    private final PasswordEncoder passwordEncoder;


    public ReservationService(ReservationRepository reservationRepository, BusApiService busApiService, BusService busService, BusRepository busRepository, PasswordEncoder passwordEncoder) {
        this.reservationRepository = reservationRepository;
        this.busApiService = busApiService;
        this.busRepository = busRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ReservationDTO saveReservation(ReservationDTO dto, User user) {
        dto.setBusnumberplate(busApiService.GetBusRoutePlaceNum(dto.getBusnumberplate()));
        Reservation reservation = Reservation.builder()
                .depart_station(dto.getDepart_station())
                .busnumber(dto.getBusnumber())
                .busnumberplate(dto.getBusnumberplate())
                .arrive_station(dto.getArrive_station())
                .username(user.getUsername())
                .rtuinum(user.getUsername() + dto.getBusnumber() + LocalDateTime.now())
                .build();

        Bus bus=Bus.builder()
                .BusNumber(dto.getBusnumber())
                .busNumberPlate(dto.getBusnumberplate())
                .Password(passwordEncoder.encode("111"))
                .Personnel(50)
                .Created_Date(LocalDateTime.now())
                .build();
        if(busRepository.findBybusNumberPlate(bus.getBusNumberPlate())==null){
            busRepository.save(bus);
        }
        reservationRepository.save(reservation);
        return ReservationDTO.builder()
                .rtuinum(reservation.getRtuinum())
                .build();
    }
    public List<Reservation> getReservationInfo(User dto) {
        List<Reservation> reservation = reservationRepository.findByUsername(dto.getUsername());
        if (reservation != null) {
            return reservation;
        } else {
            throw new RuntimeException("예약정보 없음");
        }
    }
    public void putReservationPay(User user,ReservationDTO dto){
        Reservation reservation = reservationRepository.findByrtuinum(dto.getRtuinum());
        if (reservation != null) {
            reservation.updatePay(true);
            reservationRepository.save(reservation);
        }else {
            throw new RuntimeException("예약정보 없음");
        }
    }
    public void delReservation(String dto){
        Reservation reservation = reservationRepository.findByrtuinum(dto);
        if(reservation!=null){
            reservationRepository.delete(reservation);
        }else {
            throw new RuntimeException("예약 정보 없음");
        }
    }
}
