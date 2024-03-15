package project.otb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @Schema(description = "ID", example = "user")
    private String username;
    @Schema(description = "PW")
    private String password;
}
