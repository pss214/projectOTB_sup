package project.otb.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusStationDTO{
    String rtNm;
    String busRouteId;
    String arrmsg1;
    String arrmsg2;
    String vehId1;
}

