package project.otb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import project.otb.DTO.LoginDto;
import project.otb.DTO.ResponseDTO;
import project.otb.api.BusApiService;
import project.otb.service.AdminService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ADMIN')")
public class AdminController {
    private final BusApiService busApiService;
    private final AdminService adminService;

    public AdminController(BusApiService busApiService, AdminService adminService) {
        this.busApiService = busApiService;
        this.adminService = adminService;
    }
    @PostMapping("/signin")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> AdminLogin(@RequestBody LoginDto dto){
        try {
            return ResponseEntity.ok().body(ResponseDTO.builder().status(HttpStatus.OK.value()).data(List.of(adminService.AdminLogin(dto))).build());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(ResponseDTO.builder().status(HttpStatus.BAD_REQUEST.value()).message(e.getMessage()).build());
        }
    }
    @GetMapping("/getbusroute")
    public ResponseEntity<?> getBusRouteApi(){
        return ResponseEntity.created(URI.create("/admin/busroute")).body(ResponseDTO.builder()
                .status(HttpStatus.CREATED.value()).message(busApiService.GetBusRouteApi()).build());

    }
    @GetMapping("/getbusstation")
    public ResponseEntity<?> getBusStationApi(@AuthenticationPrincipal User user){
        return ResponseEntity.created(URI.create("/admin/getbusstation")).body(ResponseDTO.builder()
                .status(HttpStatus.CREATED.value()).message(busApiService.GetBusStationAPI()).build());
    }
    @GetMapping("/userlist")
    public ResponseEntity<?> getUserList(@AuthenticationPrincipal User user){
        return ResponseEntity.ok().body(ResponseDTO.builder()
                .status(HttpStatus.OK.value()).data(List.of(adminService.AdminUserList())).message(" User 데이터를 가져왔습니다.").build());
    }
    @GetMapping("/buslist")
    public ResponseEntity<?> getBusList(@AuthenticationPrincipal User user){
        return ResponseEntity.ok().body(ResponseDTO.builder()
                .status(HttpStatus.OK.value()).data(List.of(adminService.AdminBusList())).message(" Bus 데이터를 가져왔습니다.").build());
    }
    @GetMapping("/reservationlist")
    public ResponseEntity<?> getReservationList(@AuthenticationPrincipal User user){
        return ResponseEntity.ok().body(ResponseDTO.builder()
                .status(HttpStatus.OK.value()).data(List.of(adminService.AdminReservationList())).message(" 예약 데이터를 가져왔습니다.").build());
    }
    @GetMapping("/busroutelist")
    public ResponseEntity<?> getBusRouteList(@AuthenticationPrincipal User user){
        return ResponseEntity.ok().body(ResponseDTO.builder()
                .status(HttpStatus.OK.value()).data(List.of(adminService.AdminBusRouteList())).message(" BusRoute 데이터를 가져왔습니다.").build());
    }
    @GetMapping("/busstationlist")
    public ResponseEntity<?> getBusStationList(@AuthenticationPrincipal User user){
        return ResponseEntity.ok().body(ResponseDTO.builder()
                .status(HttpStatus.OK.value()).data(List.of(adminService.AdminBusStationList())).message(" BusStation 데이터를 가져왔습니다.").build());
    }
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> DeleteUserId(@AuthenticationPrincipal User user, @PathVariable Long id){
        return ResponseEntity.created(URI.create("/admin/user")).body(ResponseDTO.builder()
                .status(HttpStatus.CREATED.value()).message(adminService.UserDelete(id)+"데이터를 삭제했습니다.").build());
    }
    @DeleteMapping("/bus/{id}")
    public ResponseEntity<?> DeleteBusId(@AuthenticationPrincipal User user,@PathVariable Long id){
        return ResponseEntity.created(URI.create("/admin/bus")).body(ResponseDTO.builder()
                .status(HttpStatus.CREATED.value()).message(adminService.BusDelete(id)+"데이터를 삭제했습니다.").build());
    }
    @DeleteMapping("/busstationall")
    public ResponseEntity<?> DeleteBusStationAll(@AuthenticationPrincipal User user){
        return ResponseEntity.created(URI.create("/admin/busstationall")).body(ResponseDTO.builder()
                .status(HttpStatus.CREATED.value()).message("버스 정류장 "+adminService.BusStationAllDlete()).build());
    }
    @DeleteMapping("/busrouteall")
    public ResponseEntity<?> DeleteBusRouteAll(@AuthenticationPrincipal User user){
        return ResponseEntity.created(URI.create("/admin/busrouteall")).body(ResponseDTO.builder()
                .status(HttpStatus.CREATED.value()).message("버스 정보 "+adminService.BusRouteAllDelete()).build());
    }
}
