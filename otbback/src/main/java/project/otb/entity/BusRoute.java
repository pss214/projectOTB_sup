package project.otb.entity;

import jakarta.persistence.*;
import lombok.Builder;

@Builder
@Entity
public class BusRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String routeid;
    String route;
}
