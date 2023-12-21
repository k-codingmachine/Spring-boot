package org.zerock.b01.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;

@SpringBootTest
@Log4j2
class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister() {
        BoardDTO boardDTO = BoardDTO.builder()
                .title("테슽흐 제목")
                .content("테슽흐 내용")
                .writer("테슽흐 작성자")
                .build();

        Long bno = boardService.register(boardDTO);
        log.info("bno ====> " + bno);
    }

    @Test
    public void testReadOne() {
        BoardDTO boardDTO = boardService.readOne(102L);
        log.info("boardDTO =====>" + boardDTO);
    }

    @Test
    public void testModify() {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(102L)
                .title("수정 제목")
                .content("수정 내용")
                .build();

        boardService.modify(boardDTO);
    }

    @Test
    public void testDelete() {
        boardService.remove(103L);
    }


    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")
                .keyword("1")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<BoardDTO> pageResponseDTO = boardService.list(pageRequestDTO);

        log.info(pageResponseDTO);
    }

}