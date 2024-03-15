package project.otb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO<T> {
    @Schema(description = "응답 번호",nullable = true)
    private int status;
    @Schema(description = "응답 메세지",nullable = true)
    private String message;
    @Schema(description = "응답 데이터",nullable = true)
    private List<T> data;
}
