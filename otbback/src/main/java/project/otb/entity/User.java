package project.otb.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, name = "user_id")
    private Long id;
    @Column(nullable = false, unique = true,length = 20)
    private String username;
    @Column(nullable = false,length = 120)
    private String password;
    @Column(nullable = false)
    private String email;
    @Column(name = "DC",nullable = false)
    private LocalDateTime Created_Date;
    @Column(name = "MD")
    private LocalDateTime modified_Date;

    public void updateUser(String email, String password){
        this.email = email;
        this.password = password;
    }
}