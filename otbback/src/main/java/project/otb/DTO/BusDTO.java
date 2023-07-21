package project.otb.DTO;

import lombok.*;

import java.time.LocalDateTime;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
    public class BusDTO {
        private String BusNumber;
        private String password;
        private LocalDateTime CD;
        private int Personnel;

        public project.otb.DTO.BusDTO form(project.otb.DTO.BusDTO user){
            return null;
        }
    }
