package org.zerock.b01.service;

import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardDTO;

public interface BoardService {

    Long register(BoardDTO boardDTO);


    BoardDTO readOne(Long bno);



   default Board boardDTOTOEntity(BoardDTO boardDTO){ //default는 구현클래스에서 오버라이딩해도 되고 안해도된다.
       Board board = Board.builder()
               .title(boardDTO.getTitle())
               .content(boardDTO.getContent())
               .writer(boardDTO.getWriter())
               .build();
       return board;
    }


    default BoardDTO entityToBoardDTO(Board board){

       return  BoardDTO.builder()
               .bno(board.getBno())
               .title(board.getTitle())
               .content(board.getContent())
               .writer(board.getWriter())
               .regDate(board.getRegDate())
               .modDate(board.getModDate())
               .build();

    }


}
