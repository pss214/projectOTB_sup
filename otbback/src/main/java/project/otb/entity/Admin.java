package project.otb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, name = "admin_id")
    private Long id;
    @Column(nullable = false, unique = true,length = 20)
    private String adminname;
    @Column(nullable = false,length = 120)
    private String password;
    @Column(nullable = false)
    private String email;
}
