package project.otb.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StationDTO{
    @Schema(description = "정류장 ID를 입력하면 버스리스트를 보내는 OTB")
    String stationid;
    @Schema(description = "버스 ID를 입력하면 정류장리스트를 보내는 OTB")
    String busrouteid;
    @Schema(description = "차량ID를 받는 객체")
    String busid;
}
