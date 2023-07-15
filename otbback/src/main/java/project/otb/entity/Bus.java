package project.otb.entity;


import jakarta.persistence.*;

@Table
@Entity
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "b_id")
    private Long id;
    @Column(name = "b_num")
    private String BusNumber;
    @Column(name = "bu_num",nullable = false)
    private String BusUniqueNumber;
}
