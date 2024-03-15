package project.otb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {
    private String adminname;
    private String password;
    private String email;
    private String token;
    private String type;
}