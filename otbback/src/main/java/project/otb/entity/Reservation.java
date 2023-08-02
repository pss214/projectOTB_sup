package project.otb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Table
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rt_id;
    private String rtuinum;//busnumber+time+username
    @Column(name = "dst")
    private String depart_station;
    @Column(name = "ast")
    private String arrive_station;
    private String busnumber;
    @Column(name = "bu_num")
    private String BusNumberPlate;
    @Column(name = "payment")
    private boolean Payment;
    private String username;


}