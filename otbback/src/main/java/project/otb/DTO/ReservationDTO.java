package project.otb.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    @Schema(description = "출발정류장", example = "22276")
    private String depart_station;
    @Schema(description = "도착정류장", example = "22276")
    private String arrive_station;
    @Schema(description = "유저 ID", nullable = true)
    private String username;
    @Schema(description = "버스명", example = "서초18")
    private String busnumber;
    @Schema(description = "버스 차량 번호", example = "서울75사9114")
    private String busnumberplate;
    @Schema(description = "결제여부", example = "true")
    private String payment;
    @Schema(description = "예약 번호", nullable = true)
    private String rtuinum;//busnumber+time+username

}
