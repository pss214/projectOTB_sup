package project.otb.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Table
@Entity
@Data
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rt_id;
    private String rtuinum;//busnumber+time+username
    @Column(name = "dst")
    private int depart_station;
    @Column(name = "ast")
    private int arrive_station;
    private String busnumber;
    @Column(name = "bu_num")
    private String BusNumberPlate;
    @Column(name = "payment")
    private boolean Payment;
    private String username;


}