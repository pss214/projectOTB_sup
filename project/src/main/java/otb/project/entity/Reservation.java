package otb.project.entity;

import lombok.Data;

import javax.persistence.*;

@Table
@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @Column(name = "DS")
    private int depart_station;
    @Column(name = "AS")
    private int arrive_station;
    @Column(nullable = false)
    private String BusNumber;
    @Column(name = "BUN")
    private String BusUniqueNumber;
}
