package project.otb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.otb.dto.ResponseDTO;
import project.otb.service.NewsService;

import java.util.List;

@RestController
@RequestMapping("/news")
@PreAuthorize("permitAll()")
public class NewsController {
    final private NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/{query}")
    public ResponseEntity<?> getBusRouteApi(@PathVariable String query){
        return ResponseEntity.ok().body(ResponseDTO.builder()
                .status(HttpStatus.OK.value()).data(List.of(newsService.NewsGetApi(query))).message("완료되었습니다.").build());

    }
    @GetMapping
    public ResponseEntity<?> getBusRouteApi(){
        return ResponseEntity.ok().body(ResponseDTO.builder()
                .status(HttpStatus.OK.value()).data(List.of(newsService.NewsGetApi(null))).message("완료되었습니다.").build());

    }
}
