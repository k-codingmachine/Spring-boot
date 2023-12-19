package org.zerock.b01.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.dto.BoardDTO;

@SpringBootTest
@Log4j2
class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister(){
        BoardDTO boardDTO = BoardDTO.builder()
                .title("테슽흐 제목")
                .content("테슽흐 내용")
                .writer("테슽흐 작성자")
                .build();

        Long bno = boardService.register(boardDTO);
        log.info("bno ====> " + bno);
    }

    @Test
    public void testReadOne(){
        BoardDTO boardDTO = boardService.readOne(102L);

        log.info("boardDTO >> " + boardDTO);
    }
}