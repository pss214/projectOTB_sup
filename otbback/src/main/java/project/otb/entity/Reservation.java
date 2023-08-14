package project.otb.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Table
@Entity
@Getter
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
    private String busnumberplate;
    private boolean payment;
    private String username;

    public void updatePay(boolean payment){
        this.payment = payment;
    }
}