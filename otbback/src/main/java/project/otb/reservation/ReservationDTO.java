package project.otb.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private int depart_station;
    private int arrive_station;
    private String username;
    private String BusNumber;
    private String BusNumberPlate;
    private boolean purchaseStatus;

}
