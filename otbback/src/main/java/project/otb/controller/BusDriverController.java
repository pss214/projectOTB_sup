package project.otb.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.otb.api.BusApiService;

@RestController
@RequestMapping("/bus-driver")
@Tag(name = "BUS",description = "버스기사 컨트롤러")
public class BusDriverController {
    private BusApiService busApiService;

    @PostMapping("/get-onoff")
    public ResponseEntity<?> BusLiveGettingOnOff(@AuthenticationPrincipal User user){

        return null;
    }
}
