package project.otb.api;

import lombok.*;

import java.net.URL;
import java.util.List;

@Builder
@Data
public class NewsDTO {
    private String status;
    private List<Articles> articles;
}
@Getter
class Articles{
    String title;
    String description;
    String url;
    String urlToImage;
}
