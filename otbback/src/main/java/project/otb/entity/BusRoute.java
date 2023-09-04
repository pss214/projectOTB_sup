package project.otb.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BusRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String routeid;
    String route;
    @Column(columnDefinition = "TEXT",length = 65534)
    String stationlist;
    @Column(columnDefinition = "TEXT",length = 65534)
    String stationlive;
    public void updateStation(String stationlist){
        this.stationlist = stationlist;
    }
    public void updatelive(String stationlive){
        this.stationlive = stationlive;
    }
}
