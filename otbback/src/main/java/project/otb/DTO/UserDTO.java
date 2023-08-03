package project.otb.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nonnull;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @Schema(description = "사용자 ID", example = "user")
    private String username;
    @Schema(description = "사용자 PW")
    private String password;
    @Schema(description = "사용자 Email", example = "username@gmail.com")
    private String email;
    @Schema(description = "사용자 Token",nullable = true)
    private String token;
    @Schema(description = "회원 생성날짜",nullable = true)
    private LocalDateTime CD;
    @Schema(description = "회원 수정날짜",nullable = true)
    private LocalDateTime MD;
}