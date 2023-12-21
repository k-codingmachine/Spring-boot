package org.zerock.b01.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.ReplyDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class ReplyServiceTest {

    @Autowired
    private ReplyServcie replyServcie;

    @Test
    public void testRegister(){
        ReplyDTO replyDTO = ReplyDTO.builder()
                .bno(104L)
                .replyText("REPLYDTO TEST")
                .replyer("replyDTODTO")
                .build();

        Long rno =
            replyServcie.register(replyDTO);

        log.info("rno >>>>>>> " +rno);
    }


    @Test
    public void testRead(){

        ReplyDTO replyDTO = replyServcie.read(9L);

       log.info(replyDTO);
    }

    @Test
    public void testModify(){
        ReplyDTO replyDTO = ReplyDTO.builder()
                .bno(3L)
                .replyText("댓글 내용 변경 테스트?")
                .build();

        replyServcie.modify(replyDTO);
    }

    @Test
    public void testDelete(){
        replyServcie.remove(3L);
    }

    @Test
    public void testGetListOfBoard(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("tcw")
                .build();

        replyServcie.getListOfBoard(100L, null);
    }













}