package project.otb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.otb.dto.ResponseDTO;
import project.otb.dto.StationDTO;
import project.otb.api.BusApiService;

import java.util.List;

@RestController
@RequestMapping("/bus")
@Tag(name = "API",description = "공공데이터에서 정보를 가져오기 위한 컨트롤러")
public class BusApiController {
    private final BusApiService busApiService;

    public BusApiController(BusApiService busApiService) {
        this.busApiService = busApiService;
    }

    @PostMapping("/information")
    @Operation(summary = "정류장 버스노선리스트",description = "정류장ID를 통해 해당 정류장을 경유하는 버스노선을 리스트로 보내는 API 입니다.")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "성공",content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "실패",content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    })
    public ResponseEntity<?> Businpormation( @RequestBody StationDTO dto){
        return ResponseEntity.ok().body(ResponseDTO.builder()
                .status(HttpStatus.OK.value()).message("완료되었습니다")
                .data(List.of(busApiService.GetBusStation(dto.getStationid())))
                .build());
    }
    @PostMapping("/route-name")
    @Operation(summary = "버스노선 정류장리스트",description = "버스노선ID를 통해 해당 버스가 경유하는 정류장을 리스트로 보내는 API 입니다.")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "성공",content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "실패",content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    })
    public ResponseEntity<?> Busroutenm( @RequestBody StationDTO dto) {
        return ResponseEntity.ok().body(ResponseDTO.builder()
                .status(HttpStatus.OK.value()).message("완료되었습니다")
                .data(List.of(busApiService.GetBusStationRoute(dto.getBusrouteid(), dto.getStationid())))
                .build());
    }
    @PostMapping("/livebyroute")
    @Operation(summary = "버스도착정보 정류장리스트",description = "버스노선ID를 통해 해당노선의 전체 정류소 도착예정정보를 리스트로 보내는 API 입니다.")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "성공",content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "실패",content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    })
    public ResponseEntity<?> BusLiveByRoute(@RequestBody StationDTO dto){
        return ResponseEntity.ok().body(ResponseDTO.builder()
                .status(HttpStatus.OK.value()).message("완료되었습니다")
                .data(List.of(busApiService.GetBusLiveByRoute(dto.getBusrouteid())))
                .build());
    }
}
