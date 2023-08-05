package project.otb.DTO;

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
    @Schema(description = "버스ID와 정류장 ID를 오가는 OTB")
    String id;
}