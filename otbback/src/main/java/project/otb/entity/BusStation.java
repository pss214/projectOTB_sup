package project.otb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BusStation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String stationid;
    String stationuniid;
    String stationname;
}
