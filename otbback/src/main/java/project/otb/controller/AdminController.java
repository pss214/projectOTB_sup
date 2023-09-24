package project.otb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.otb.DTO.ResponseDTO;
import project.otb.api.BusApiService;

@RestController
@RequestMapping("/admin")

public class AdminController {
    private final BusApiService busApiService;

    public AdminController(BusApiService busApiService) {
        this.busApiService = busApiService;
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
