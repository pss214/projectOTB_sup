package otb.project.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "user")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Column(updatable = false, name = "id")
    private Long id;
    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String username;
    @Getter
    @Setter
    @Column(nullable = false)
    private String password;
    @Getter
    @Setter
    @Column(nullable = false)
    private String email;
    @Getter
    @Setter
    @Column(name = "DC",nullable = false)
    private LocalDateTime Created_Date;
    @Getter
    @Setter
    @Column(name = "ED", unique = false)
    private LocalDateTime Edit_Date;
}
