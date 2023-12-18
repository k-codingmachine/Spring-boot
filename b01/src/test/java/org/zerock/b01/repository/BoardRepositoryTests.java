package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.b01.domain.Board;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testInsert(){
        for(int i=0; i<=100; i++) {
            Board board = Board.builder()
                    .title("title.." +i)
                    .content("content" + i)
                    .writer("user" + (i%10))
                    .build();

            Board result = boardRepository.save(board);
            log.info("BNO : " +result.getBon());
        }
    }

    @Test
    public void testSelect(){
        Long bno = 100L;

/*        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();*/

        Board board = boardRepository.findById(bno).orElseThrow();

        log.info(board);
    }

    @Test
    public void testUpdate(){
        Long bno = 100L;

        Board board = boardRepository.findById(bno).orElseThrow();
        log.info("board >> " + board);

        board.change("update....", "update content....");
        log.info("board2 >> " + board);
        boardRepository.save(board);
        log.info("board3 >> " + board);
    }

    @Test
    public void testDelete(){
        boardRepository.deleteById(100L);
    }

    @Test
    public void testGetList(){
//        boardRepository.findAll().forEach(list -> log.info(list));

        List<Board> list = boardRepository.findAll();
        for(Board b : list){
            log.info(b);
        }
    }

    @Test
    public void testPaging(){
        Pageable pageable = PageRequest.of(1,10, Sort.by("bno").descending());

        Page<Board> result = boardRepository.findAll(pageable);

        log.info(result.getTotalElements());
        log.info(result.getTotalPages());

        result.getContent().forEach(list -> log.info(list));
    }

}