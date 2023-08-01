package project.otb.DTO;

import lombok.*;

import java.time.LocalDateTime;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusDTO {
    private String busnumber;
    private String password;
    private LocalDateTime CD;
    private int personnel;
    private String busnumberplate;
    private String token;
}

