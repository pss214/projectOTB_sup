package project.otb.service;

import org.springframework.stereotype.Service;
import project.otb.dto.SaveDTO;
import project.otb.entity.Board;
import project.otb.repository.BoardRepository;

import java.util.List;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }
    public String saveBoard(SaveDTO dto){
        boardRepository.save(Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .type(dto.getType())
                .build());
        return "저장 되었습니다.";
    }
    public List<Board> listBoard(String type){
        return boardRepository.findByType(type);
    }

    public Board OneBoard(String id){
        Long to = Long.parseLong(id);
        if(boardRepository.findById(to).isPresent())
            return boardRepository.findById(to).get();
        else
            return null;
    }
}
