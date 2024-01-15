package project.otb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.otb.dto.ResponseDTO;
import project.otb.dto.SaveDTO;
import project.otb.entity.Board;
import project.otb.service.BoardService;
import project.otb.service.ImageService;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/report-board")
public class ReportBoardController {
    private final BoardService boardService;
    private final ImageService imageService;
    public ReportBoardController(BoardService boardService, ImageService imageService) {
        this.boardService = boardService;
        this.imageService = imageService;
    }

    @PostMapping("/image/upload")
    public ResponseEntity<?> ImageUpload(@RequestParam(value = "upload") MultipartFile request) {
        try{
            return ResponseEntity.created(URI.create("/image/upload")).body(ResponseDTO.builder()
                    .data(List.of(imageService.postReportImage(request))).build());
        }catch (IOException e){
            return ResponseEntity.internalServerError().body(e);
        }

    }

    @PostMapping("/save")
    public ResponseEntity<?> saveBoard(@RequestBody SaveDTO dto){
        return ResponseEntity.created(URI.create("/board/save")).body(ResponseDTO.builder().
                status(HttpStatus.CREATED.value()).message(boardService.saveBoard(dto)).build());
    }
    @GetMapping("/list/{type}")
    public ResponseEntity<?> listBoard(@PathVariable String type){
        List<Board> boards = boardService.listBoard(type);
        if(boards != null)
            return ResponseEntity.ok().body(ResponseDTO.builder()
                    .data(List.of(boards)).build());
        else
            return ResponseEntity.badRequest().body(ResponseDTO.builder()
                    .message("게시물이 없습니다").build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> Boardpage(@PathVariable("id")String id){
        Board board =boardService.OneBoard(id);
        if (board!=null)
            return ResponseEntity.ok().body(ResponseDTO.builder()
                    .data(List.of(board)).build());
        else
            return ResponseEntity.badRequest().body("게시물을 찾지 못했습니다.");
    }
    @PostMapping("/{id}")
    public ResponseEntity<?> modifyBoard(@RequestBody SaveDTO dto){
        return null;
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable("id")String id){
        return null;
    }

}
