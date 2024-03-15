package project.otb.dto.apidto;

import lombok.*;

import java.util.List;

@Builder
@Data
public class NewsDTO {
    private String status;
    private List<Articles> articles;

    @Getter
    public class Articles {
        String title;
        String description;
        String url;
        String urlToImage;
    }
}
