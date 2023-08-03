package project.otb.DTO;

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
    private String depart_station;
    private String arrive_station;
    private String username;
    private String busnumber;
    private String busnumberplate;
    private String payment;
    private String rtuinum;//busnumber+time+username
    private String member;

}
