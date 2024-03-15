package project.otb.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.otb.dto.AdminDTO;
import project.otb.dto.LoginDto;
import project.otb.entity.*;
import project.otb.repository.*;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final BusRepository busRepository;
    private final BusRouteRepository busRouteRepository;
    private final BusStationRepository busStationRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final BusApiService busApiService;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public AdminService(AdminRepository adminRepository, BusRepository busRepository, BusRouteRepository busRouteRepository,
                        BusStationRepository busStationRepository, ReservationRepository reservationRepository, UserRepository userRepository,
                        BusApiService busApiService, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.adminRepository = adminRepository;
        this.busRepository = busRepository;
        this.busRouteRepository = busRouteRepository;
        this.busStationRepository = busStationRepository;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.busApiService = busApiService;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }
    public AdminDTO AdminLogin(final LoginDto dto){
        Admin admin = adminRepository.findByAdminname(dto.getUsername());
        if(admin!=null&& passwordEncoder.matches(dto.getPassword(), admin.getPassword())) {
            String token = tokenProvider.createToken(String.format("%s:%s", admin.getAdminname(), "ROLE_ADMIN"));

            return AdminDTO.builder()
                    .adminname(admin.getAdminname())
                    .email(admin.getEmail())
                    .token(token)
                    .type("admin")
                    .build();
        }
        else {
            return null;
        }
    }
    public List<User> AdminUserList (){
        return userRepository.findAll();
    }
    public List<Bus> AdminBusList (){
        return busRepository.findAll();
    }
    public List<Reservation> AdminReservationList (){
        return reservationRepository.findAll();
    }
    public List<BusRoute> AdminBusRouteList (){
        return busRouteRepository.findAll();
    }
    public List<BusStation> AdminBusStationList (){
        return busStationRepository.findAll();
    }
    public String UserDelete (Long id){
        Optional<User> user=userRepository.findById(id);
        userRepository.deleteById(id);
        return user.get().getUsername();
    }
    public String BusDelete (Long id){
        Optional<Bus> bus = busRepository.findById(id);
        userRepository.deleteById(id);
        return "삭제 완료";
    }
    public String BusStationAllDlete(){
        busStationRepository.deleteAll();
        return "삭제 완료";
    }
    public String BusRouteAllDelete(){
        busRouteRepository.deleteAll();
        return "삭제 완료";
    }
    public String getBusRouteApi(){
        String response = busApiService.GetBusRouteApi();
        return response;
    }
    public String getBusStationApi(){
        String response = busApiService.GetBusStationAPI();
        return response;
    }
}
