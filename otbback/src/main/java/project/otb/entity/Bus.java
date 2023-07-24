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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "b_id")
    private Long id;
    @Column(name = "b_num",nullable = false)
    private String BusNumber;
    @Column(name = "b_password",nullable = false)
    private String Password;
    @Column(name = "DC",nullable = false)
    private LocalDateTime Created_Date;
    @Column(unique = true,nullable = false)
    private String busNumberPlate;
    @Column(name = "b_personnel", nullable = false)
    private int Personnel;
}
