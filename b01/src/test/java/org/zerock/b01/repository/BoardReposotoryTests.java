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
import org.zerock.b01.dto.BoardListReplyCountDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class BoardReposotoryTests {

    @Autowired
    private BoardReposotory boardReposotory;

    @Test
    public void testInsert(){
        for(int i=0; i<=100; i++) {
            Board board = Board.builder()
                    .title("title.." +i)
                    .content("content..." +i)
                    .writer("user"+ (i%10))
                    .build();

           Board result = boardReposotory.save(board);
           log.info("BNO : " + result.getBno());
        }
    }

    @Test
    public void testSelect(){
        Long bno  = 100L;

//       Optional<Board> result = boardReposotory.findById(bno);
//       Board board = result.orElseThrow();

       Board board  = boardReposotory.findById(bno).orElseThrow();

       log.info(board);
    }


    @Test
    public void testUPdate(){
        Long bno  = 100L;

        Board board = boardReposotory.findById(bno).orElseThrow();
        log.info("board >> " + board);

        board.change("update title222....", "update content2222....");

        log.info("board2 >> " + board);
        boardReposotory.save(board);

        log.info("board3 >> " + board);
    }

    @Test
    public void testDelete(){
        boardReposotory.deleteById(100L);
    }

    @Test
    public void testGetList(){
        //boardReposotory.findAll().forEach(list-> log.info(list));

        List<Board> list = boardReposotory.findAll();
        for(Board b : list)
            log.info(b);
    }


    @Test
    public void testPaging(){
       Pageable pageable =  PageRequest.of(2,10, Sort.by("bno").descending());

       Page<Board> result =  boardReposotory.findAll(pageable);

       log.info(result.getTotalElements());
       log.info(result.getTotalPages());


       result.getContent().forEach(list -> log.info(list));
    }


    @Test
    public void testWriter(){
        boardReposotory.findByWriter("user1")
                .forEach(list-> log.info(list));
    }

    @Test
    public void testWriterAndTitle(){
        boardReposotory
                .findByWriterAndTitle("user1","title..1")
                .forEach(list-> log.info(list));
    }

    @Test
    public void testTitleLike(){
        boardReposotory
                .findByTitleLike("%1%")
                .forEach(list-> log.info(list));
    }


    @Test
    public void testWriter2(){
        boardReposotory.findByWriter2("user1")
                .forEach(list-> log.info(list));
    }

    @Test
    public void testTitle2(){
        boardReposotory.findByTitile2("2")
                .forEach(list-> log.info(list));
    }

    @Test
    public void testKeyword(){
        Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").descending());

       Page<Board>  result = boardReposotory.findByKeyword("1",pageable);
       log.info(result.getTotalElements());
       log.info(result.getTotalPages());
       result.getContent().forEach(board -> log.info(board));

    }


    @Test
    public void  testSearch1(){
      Pageable pageable =  PageRequest.of(1,10, Sort.by("bno")
                .descending());

        boardReposotory.search1(pageable);
    }


    @Test
    public void testSearchAll(){
        String[] types = {"t", "c","w"};

        String keyword = "1";

        Pageable pageable=
        PageRequest.of(0,10,Sort.by("bno")
                .descending());

       Page<Board>  result =  boardReposotory.searchAll(types, keyword, pageable);

       log.info("---------------------------------");
       log.info(result.getTotalElements());
       log.info(result.getTotalPages());
       log.info(result.getSize());
       log.info(result.getNumber());
       log.info(result.hasPrevious());
       log.info(result.hasNext());
       log.info("---------------------------------");

    }



    @Test
    public void testSearchReplyCount(){

        String[] types = { "t", "c","w"};

        String keyword = "1";

        Pageable pageable =
                PageRequest.of(0,10,
                        Sort.by("bno").descending());

        Page<BoardListReplyCountDTO>  result =
                boardReposotory.searchWithReplyCount(types,keyword, pageable);

        log.info(result.getTotalElements());
        log.info(result.getTotalPages());
        log.info(result.getSize());
        log.info(result.getNumber());
        log.info("Next : " + result.hasNext());
        log.info("Prev : " + result.hasPrevious() );

        result.getContent().forEach(list-> log.info(list));

    }




















}