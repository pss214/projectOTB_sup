package otb.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
@Builder
@Data
@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, name = "id")
    private Long id;
    @Column(nullable = false, unique = true,length = 20)
    private String username;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false,length = 120)
    private String password;
    @Column(nullable = false)
    private String email;
    @Column(name = "DC",nullable = false)
    private LocalDateTime Created_Date;
    @Column(name = "MD")
    private LocalDateTime modified_Date;
}
