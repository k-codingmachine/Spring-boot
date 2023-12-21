package org.zerock.b01.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.BoardListReplyCountDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.repository.BoardReposotory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements  BoardService{

    private final BoardReposotory boardReposotory;

    private final ModelMapper modelMapper;

    @Override
    public Long register(BoardDTO boardDTO) {

       Board board = modelMapper.map(boardDTO, Board.class);

       log.info(board);

       Long bno = boardReposotory.save(board).getBno();

        return bno;
    }

    @Override
    public BoardDTO readOne(Long bno) {

//        Optional<Board> result = boardReposotory.findById(bno);
//        Board board = result.orElseThrow();

        Board board = boardReposotory.findById(bno).orElseThrow();

        BoardDTO boardDTO =modelMapper.map(board, BoardDTO.class);

        return boardDTO;
    }

    @Override
    public void midify(BoardDTO boardDTO) {

        Board board = boardReposotory.findById(boardDTO.getBno()).orElseThrow();

        board.change(boardDTO.getTitle(), boardDTO.getContent());

        boardReposotory.save(board);
    }

    @Override
    public void remove(Long bno) {

        boardReposotory.deleteById(bno);
    }

    @Override
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();  //tcw   ==> t c w
        String keyword = pageRequestDTO.getKeyword();

        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<Board> result = boardReposotory.searchAll(types, keyword, pageable);

//        result.getTotalElements();
//        result.getTotalPages();
//        result.getContent().forEach(board -> log.info(board));

        List<BoardDTO> dtoList = result.getContent().stream().
                map(board -> modelMapper.map(board, BoardDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();  //tcw   ==> t c w
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");


        Page<BoardListReplyCountDTO> result = boardReposotory.searchWithReplyCount(types, keyword, pageable);

        return PageResponseDTO.<BoardListReplyCountDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();
    }


//    @Override
//    public Long register(BoardDTO boardDTO) {
//
//       Board board  = boardDTOTOEnity(boardDTO);
//
//       log.info("register ==> " + board);
//       Long bno = boardReposotory.save(board).getBno();
//
//        return bno;
//    }

//    @Override
//    public BoardDTO readOne(Long bno) {
//
//        Board board = boardReposotory.findById(bno).orElseThrow();
//
////        BoardDTO boardDTO  = entityToBoardDTO(board);
//
//        return entityToBoardDTO(board);
//    }


}
