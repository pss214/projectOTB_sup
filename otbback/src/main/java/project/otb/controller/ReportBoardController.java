package project.otb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
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
    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/save")
    public ResponseEntity<?> saveBoard(@RequestBody SaveDTO dto, @AuthenticationPrincipal User user){
        return ResponseEntity.created(URI.create("/board/save")).body(ResponseDTO.builder().
                status(HttpStatus.CREATED.value()).message(boardService.saveBoard(dto, user)).build());
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
    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/{id}")
    public ResponseEntity<?> modifyBoard(@RequestBody SaveDTO dto,@AuthenticationPrincipal User user){
        return null;
    }
    @PreAuthorize("hasAnyRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable("id")Long id, @AuthenticationPrincipal User user){
        try {
            boardService.delBoard(id);
            return ResponseEntity.created(URI.create("/report-board/"+id)).body(ResponseDTO.builder()
                    .status(HttpStatus.CREATED.value())
                    .message("삭제가 완료되었습니다")
                    .build());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

}
