package project.otb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import project.otb.DTO.LoginDto;
import project.otb.DTO.ResponseDTO;
import project.otb.api.BusApiService;
import project.otb.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final BusApiService busApiService;
    private final AdminService adminService;

    public AdminController(BusApiService busApiService, AdminService adminService) {
        this.busApiService = busApiService;
        this.adminService = adminService;
    }
    @PostMapping("/signin")
    public ResponseEntity<?> AdminLogin(@RequestBody LoginDto dto){
        try {
            return ResponseEntity.ok().body(ResponseDTO.builder().status(HttpStatus.OK.value()).data(List.of(adminService.AdminLogin(dto))).build());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(ResponseDTO.builder().status(HttpStatus.BAD_REQUEST.value()).message(e.getMessage()).build());
        }
    }
    @GetMapping("/busroute")
    public ResponseEntity<?> getBusRouteApi(){

        String response = busApiService.GetBusRouteApi();
        return ResponseEntity.ok().body(ResponseDTO.builder()
                .status(HttpStatus.CREATED.value()).message(response).build());
    }
    @GetMapping("/busstation")
    public ResponseEntity<?> getBusStationApi(){
        String response = busApiService.GetBusStationAPI();
        return ResponseEntity.ok().body(ResponseDTO.builder()
                .status(HttpStatus.CREATED.value()).message(response).build());
    }
}
