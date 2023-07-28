package project.otb.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Table
@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Rt_Id;
    private String rtuinum;//busnumber+time+username
    @Column(name = "dst")
    private int depart_station;
    @Column(name = "ast")
    private int arrive_station;
    @Column(nullable = false)
    private String BusNumber;
    @Column(name = "bu_num")
    private String BusUniqueNumber;
    @Column(name = "purchase_status")
    @ColumnDefault("0")
    private boolean purchaseStatus;

}