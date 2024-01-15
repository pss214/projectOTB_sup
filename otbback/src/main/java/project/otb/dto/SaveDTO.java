package project.otb.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveDTO {
    private String title;
    private String content;
    private String type;
}
