package project.otb.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusDTO {
    @Schema(description = "버스 ID", example = "서초18")
    private String busnumber;
    @Schema(description = "버스 PW")
    private String password;
    @Schema(description = "버스회원 생성날짜",nullable = true)
    private LocalDateTime CD;
    @Schema(description = "버스 수용인원", example = "20")
    private int personnel;
    @Schema(description = "버스 차량번호",example = "서울75사9114")
    private String busnumberplate;
    @Schema(description = "버스 Token",nullable = true)
    private String token;
    private String type;
}