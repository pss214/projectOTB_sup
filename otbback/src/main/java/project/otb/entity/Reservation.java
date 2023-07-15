package project.otb.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table
@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Rt_Id;
    @Column(name = "dst")
    private int depart_station;
    @Column(name = "ast")
    private int arrive_station;
    @Column(nullable = false)
    private String BusNumber;
    @Column(name = "bu_num")
    private String BusUniqueNumber;
}
