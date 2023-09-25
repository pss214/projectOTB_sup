package project.otb.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.otb.DTO.AdminDTO;
import project.otb.DTO.LoginDto;
import project.otb.entity.*;
import project.otb.repositiry.*;
import project.otb.security.TokenProvider;

import java.util.List;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final BusRepository busRepository;
    private final BusRouteRepository busRouteRepository;
    private final BusStationRepository busStationRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public AdminService(AdminRepository adminRepository, BusRepository busRepository, BusRouteRepository busRouteRepository,
                        BusStationRepository busStationRepository, ReservationRepository reservationRepository, UserRepository userRepository,
                        PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.adminRepository = adminRepository;
        this.busRepository = busRepository;
        this.busRouteRepository = busRouteRepository;
        this.busStationRepository = busStationRepository;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }
    public AdminDTO AdminLogin(final LoginDto dto){
        Admin admin = adminRepository.findByAdminname(dto.getUsername());
        if(admin!=null&& passwordEncoder.matches(dto.getPassword(), admin.getPassword())) {
            String token = tokenProvider.createToken(String.format("%s:%s", admin.getAdminname(), "ADMIN"));

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
    public List<User> AdminUserList (org.springframework.security.core.userdetails.User user){
        Admin admin = adminRepository.findByAdminname(user.getUsername());
        if(admin!=null){
            return userRepository.findAll();
        }else {
            throw new RuntimeException("오류!");
        }
    }
    public List<Bus> AdminBusList (org.springframework.security.core.userdetails.User user){
        Admin admin = adminRepository.findByAdminname(user.getUsername());
        if(admin!=null){
            return busRepository.findAll();
        }else {
            throw new RuntimeException("오류!");
        }
    }
    public List<Reservation> AdminReservationList (org.springframework.security.core.userdetails.User user){
        Admin admin = adminRepository.findByAdminname(user.getUsername());
        if(admin!=null){
            return reservationRepository.findAll();
        }else {
            throw new RuntimeException("오류!");
        }
    }
    public List<BusRoute> AdminBusRouteList (org.springframework.security.core.userdetails.User user){
        Admin admin = adminRepository.findByAdminname(user.getUsername());
        if(admin!=null){
            return busRouteRepository.findAll();
        }else {
            throw new RuntimeException("오류!");
        }
    }
    public List<BusStation> AdminBusStationList (org.springframework.security.core.userdetails.User user){
        Admin admin = adminRepository.findByAdminname(user.getUsername());
        if(admin!=null){
            return busStationRepository.findAll();
        }else {
            throw new RuntimeException("오류!");
        }
    }
    public String UserDelete (org.springframework.security.core.userdetails.User user, Long id){
        Admin admin = adminRepository.findByAdminname(user.getUsername());
        if(admin!=null){
            userRepository.deleteById(id);
            return "삭제 완료";
        }else {
            throw new RuntimeException("오류!");
        }
    }
    public String BusDelete (org.springframework.security.core.userdetails.User user, Long id){
        Admin admin = adminRepository.findByAdminname(user.getUsername());
        if(admin!=null){
            userRepository.deleteById(id);
            return "삭제 완료";
        }else {
            throw new RuntimeException("오류!");
        }
    }
}
