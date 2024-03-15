package project.otb.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import project.otb.dto.SaveDTO;
import project.otb.entity.Board;
import project.otb.repository.BoardRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }
    public String saveBoard(SaveDTO dto, User user){
        boardRepository.save(Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .type(dto.getType())
                .views(0)
                .date(LocalDate.now())
                .username(user.getUsername())
                .build());
        return "저장 되었습니다.";
    }
    public List<Board> listBoard(String type){
        return boardRepository.findByTypeOrderByIdDesc(type);
    }

    public Board OneBoard(String id){
        Long to = Long.parseLong(id);
        if(boardRepository.findById(to).isPresent()){
            Board board = boardRepository.findById(to).get();
            board.addview();
            boardRepository.save(board);
            return board;
        }

        else
            return null;
    }
    public void delBoard(Long id){
        if(boardRepository.findById(id).isPresent()){
            Board board = boardRepository.findById(id).get();
            boardRepository.delete(board);
        }else{
            throw new RuntimeException("찾을 수 없는 게시물");
        }

    }
}
