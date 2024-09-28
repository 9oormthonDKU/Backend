package jpautils.woojuboard.service;

import jpautils.woojuboard.model.DTO.request.BoardDeleteRequest;
import jpautils.woojuboard.model.DTO.request.BoardModifyRequest;
import jpautils.woojuboard.model.DTO.request.BoardPostRequest;
import jpautils.woojuboard.model.DTO.response.BoardListResponse;
import jpautils.woojuboard.model.DTO.response.BoardResponse;
import jpautils.woojuboard.model.DeleteStatus;
import jpautils.woojuboard.model.entity.Board;
import jpautils.woojuboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    @Autowired
    private final BoardRepository boardRepository;
    @Transactional
    public BoardResponse makeBoard(BoardPostRequest boardPostrequest){

        Board board = new Board();
        board.setTitle(boardPostrequest.getTitle());
        board.setContent(boardPostrequest.getContent());
        board.setDeleteStatus(DeleteStatus.ACTIVE);

        return BoardResponse.from(boardRepository.save(board));
    }

    // 참고 부분 : 리스트 페이징
    public List<BoardListResponse> searchList(int page,int size){
        return boardRepository.findAllByDeleteStatus(
                DeleteStatus.ACTIVE,
                PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,"boardNumber"))
        ).map(BoardListResponse::from).toList();
    }

    // 참고 부분 : 예외 처리 , 매핑
    public BoardResponse searchBoard(Long boardNumber){
        return boardRepository.findBoardWithContentsByBoardNumber(boardNumber)
                .map(BoardResponse::from)
                .orElseThrow(()-> new RuntimeException("존재하지 않는 게시글"));

    }


    @Transactional
    public String deleteBoard(BoardDeleteRequest boardDeleteRequest){
        Optional<Board> boardOptional = boardRepository.findById(boardDeleteRequest.getBoardNumber());
        Board board = boardOptional.orElseThrow(()->new RuntimeException());

        board.setDeleteStatus(DeleteStatus.DELETE);
        boardRepository.delete(board);
        return "Deleted";
    }

    @Transactional
    public BoardResponse modify(BoardModifyRequest boardModifyRequest){
        Optional<Board> boardOptional = boardRepository.findById(boardModifyRequest.getBoardNumber());

        Board board = boardOptional.orElseThrow(()->new RuntimeException());

        if (boardModifyRequest.getTitle() != null) {
            board.setTitle(boardModifyRequest.getTitle());
        }
        if (boardModifyRequest.getContent() != null) {
            board.setContent(boardModifyRequest.getContent());
        }

        return BoardResponse.from(boardRepository.save(board));

    }
}
