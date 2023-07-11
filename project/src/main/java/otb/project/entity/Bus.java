package otb.project.entity;

import javax.persistence.*;

@Table
@Entity
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BN")
    private String BusNumber;
    @Column(name = "BUN",nullable = false)
    private String BusUniqueNumber;
}
