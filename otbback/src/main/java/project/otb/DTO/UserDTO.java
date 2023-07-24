package project.otb.DTO;

import jakarta.annotation.Nonnull;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String password;
    private String email;
    private String token;
    private LocalDateTime CD;
    private LocalDateTime MD;
}