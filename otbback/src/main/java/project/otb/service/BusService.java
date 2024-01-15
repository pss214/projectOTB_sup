package project.otb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.otb.dto.BusDTO;
import project.otb.dto.BusLiveByRouteDTO;
import project.otb.dto.LoginDto;
import project.otb.dto.ReservationDTO;
import project.otb.entity.Bus;
import project.otb.entity.Reservation;
import project.otb.repository.BusRouteRepository;
import project.otb.repository.ReservationRepository;
import project.otb.repository.UserRepository;
import project.otb.repository.BusRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BusService {
    private final BusRepository busRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final BusRouteRepository busRouteRepository;
    private final BusApiService busApiService;

    public BusService(BusRepository busRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider, UserRepository userRepository, ReservationRepository reservationRepository, BusRouteRepository busRouteRepository, BusApiService busApiService) {
        this.busRepository = busRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.busRouteRepository = busRouteRepository;
        this.busApiService = busApiService;
    }

    public BusDTO create(final BusDTO entity) {
        if(busRepository.existsBybusNumberPlate(entity.getBusnumberplate())&&userRepository.existsByUsername(entity.getBusnumberplate())){
            throw new RuntimeException("아이디가 존재합니다!");
        }else {

                Bus bus = Bus.builder()
                        .BusNumber(entity.getBusnumber())
                        .busNumberPlate(entity.getBusnumberplate())
                        .Password(passwordEncoder.encode(entity.getPassword()))
                        .Personnel(entity.getPersonnel())
                        .Created_Date(LocalDateTime.now())
                        .build();
                busRepository.save(bus);
                return null;
        }
    }
    public BusDTO getLogin(final LoginDto dto) {
        Bus bus = busRepository.findBybusNumberPlate(dto.getUsername());
        if(bus!=null&&passwordEncoder.matches(dto.getPassword(), bus.getPassword())){
            String token = tokenProvider.createToken(String.format("%s:%s", bus.getBusNumberPlate(), "USER"));
            return BusDTO.builder()
                    .busnumberplate(bus.getBusNumberPlate())
                    .token(token)
                    .busnumber(bus.getBusNumber())
                    .personnel(bus.getPersonnel())
                    .type("bus")
                    .build();
        }else {
            return null;
        }
    }
    public List<BusLiveByRouteDTO> LiveStationInAndOut(User user){
        Bus bus = busRepository.findBybusNumberPlate(user.getUsername());
        if(bus!= null){
            List<BusLiveByRouteDTO> stationlist = busApiService.GetBusLiveByRoute(busRouteRepository.findByrouteEquals(bus.getBusNumber()).getRouteid());
            List<Reservation> reservations = reservationRepository.findBybusnumberplate(user.getUsername());
            for (int i = 0; i < stationlist.size(); i++) {
                for (Reservation reservation : reservations) {
                    if (Objects.equals(stationlist.get(i).getArsId(), reservation.getDepart_station())) {
                        BusLiveByRouteDTO save = stationlist.get(i);
                        stationlist.set(i, BusLiveByRouteDTO.builder()
                                .stNm(save.getStNm())
                                .arsId(save.getArsId())
                                .rtNm(save.getRtNm())
                                .plainNo1(save.getPlainNo1())
                                .arrmsg1(save.getArrmsg1())
                                .nstnId1(save.getNstnId1())
                                .station_in(true)
                                .station_out(save.isStation_out())
                                .build());
                        System.out.println(stationlist.get(i));
                    }
                    if (Objects.equals(stationlist.get(i).getArsId(), reservation.getArrive_station())) {
                        BusLiveByRouteDTO save = stationlist.get(i);
                        stationlist.set(i, BusLiveByRouteDTO.builder()
                                .stNm(save.getStNm())
                                .arsId(save.getArsId())
                                .rtNm(save.getRtNm())
                                .plainNo1(save.getPlainNo1())
                                .arrmsg1(save.getArrmsg1())
                                .nstnId1(save.getNstnId1())
                                .station_in(save.isStation_in())
                                .station_out(true)
                                .build());
                        System.out.println(stationlist.get(i));
                    }
                }
            }
            return stationlist;
        }else {
            throw new RuntimeException("버스 정보 없음");
        }
    }
    public Reservation QRScan(ReservationDTO dto){
        Reservation reservation = reservationRepository.findByrtuinum(dto.getRtuinum());
        if(reservation!= null){
            reservationRepository.delete(reservation);
            return reservation;
        }else {
            throw new RuntimeException("예약 정보 없음");
        }
    }
}
