package org.zerock.b01.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.b01.domain.Board;
import org.zerock.b01.repository.search.BoardSearch;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch{




















    List<Board> findBoardByWriter(String writer);
    List<Board> findByWriterAndTitle(String writer, String title); /*필드명 첫글자는 무조건 대문자로*/

    List<Board> findByTitleLike(String title);

    @Query("select i from Board i where i.writer = :writer")
    List<Board> findByWriter2(@Param("writer") String writer);

    @Query("select b from Board b where b.title like %:title% order by b.bno desc")
    List<Board> findByTitle(@Param("title") String title);


    @Query(value= "select * from board where title like %:title% order by bno desc", nativeQuery = true)
    List<Board> findByTitle2(@Param("title") String title);


    @Query("select b from Board b where b.title like concat('%', :keyword, '%')")
    Page<Board> findBykeyword(@Param("keyword")String keyword, Pageable pageable);
}
