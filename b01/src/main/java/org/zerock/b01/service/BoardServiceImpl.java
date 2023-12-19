package org.zerock.b01.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.repository.BoardRepository;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    @Override
    public Long register(BoardDTO boardDTO) {

        Board board = boardDTOTOEntity(boardDTO);

        log.info("register >> " +board);
        Long bno = boardRepository.save(board).getBno();

        return bno;
    }

    @Override
    public BoardDTO readOne(Long bno) {

        Board board = boardRepository.findById(bno).orElseThrow();

//        BoardDTO boardDTO = entityToBoardDTO(board);


        return entityToBoardDTO(board);
    }


}
