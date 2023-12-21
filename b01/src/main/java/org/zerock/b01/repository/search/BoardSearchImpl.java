package org.zerock.b01.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.querydsl.QPageRequest;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.QBoard;
import org.zerock.b01.domain.QReply;
import org.zerock.b01.dto.BoardListReplyCountDTO;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{


    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {

        QBoard board = QBoard.board;

        JPQLQuery<Board> query = from(board);  //select * from board

        query.where(board.title.contains("1")); //where title like "'%'1'%'"

       //paging
       this.getQuerydsl().applyPagination(pageable, query) ;


        List<Board> list = query.fetch();
        long count = query.fetchCount();

        log.info("count : " + count);
        list.forEach(board1 -> log.info(board1));

        return null;
    }

    @Override                       //tcw
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;

        JPQLQuery<Board> query  = from(board); //select * from board

        if(( types != null && types.length>0)  && keyword != null){
            BooleanBuilder  booleanBuilder = new BooleanBuilder();

            for(String type : types){
                switch (type){
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            }  //end for
            query.where(booleanBuilder);
        }

        query.where(board.bno.gt(0L));  // where bno >0

        log.info("query ==> " + query);

        //페이징 처리
        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> list= query.fetch();
        Long count = query.fetchCount();


        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        JPQLQuery<Board> query = from(board); //select * from board
        query.leftJoin(reply).on(reply.board.eq(board));
        query.groupBy(board);


        if(( types != null && types.length>0)  && keyword != null){
            BooleanBuilder  booleanBuilder = new BooleanBuilder();

            for(String type : types){
                switch (type){
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));
                        break;
                }
            }  //end for
            query.where(booleanBuilder);
        }

        query.where(board.bno.gt(0L));  // where bno >0

        query.fetch();

        /*
        private Long bno;
        private String title;
        private String writer;
        private LocalDateTime regDate;

        private Long replyCount;
         */

        JPQLQuery<BoardListReplyCountDTO> dtoQuery = query.select(
                Projections.bean(BoardListReplyCountDTO.class,
                        board.bno,
                        board.title,
                        board.writer,
                        board.regDate,
                        reply.count().as("replyCount"))
        );

        log.info("dtoQuery = > " + dtoQuery);

        this.getQuerydsl().applyPagination(pageable, dtoQuery);
        List<BoardListReplyCountDTO> dtoList =  dtoQuery.fetch();
        Long count = dtoQuery.fetchCount();



        return new PageImpl<>(dtoList, pageable,count);
    }
}


















