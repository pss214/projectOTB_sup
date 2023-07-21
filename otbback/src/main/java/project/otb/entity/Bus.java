package project.otb.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "b_id")
    private Long id;
    @Column(name = "b_num")
    private String BusNumber;
    @Column(name = "b_password")
    private String Password;
    @Column(name = "DC",nullable = false)
    private LocalDateTime Created_Date;
    @Column(name = "bu_num",nullable = false)
    private String BusUniqueNumber;
    @Column(name = "bu_Personnel", nullable = false)
    private int Personnel;
}
