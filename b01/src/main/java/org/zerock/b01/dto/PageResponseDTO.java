package org.zerock.b01.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageResponseDTO<E> {

    private int page;
    private int size;
    private int total;

    private int start;      //시작 페이지
    private int end;        // 끝 페이지

    private boolean prev;   // 이전 버튼
    private boolean next;   // 다음 버튼

    private List<E> dtoList;// 전체 데이터

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total){

        if(total <= 0){
            return;
        }

        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        this.total = total;
        this.dtoList = dtoList;

        this.end =   (int)(Math.ceil(this.page / 10.0 )) *  10; //페이지 값 계산

        this.start = this.end - 9;      //시작 페이지 보여주겠다.

        int last =  (int)(Math.ceil((total/(double)size)));

        this.end =  end > last ? last: end;

        this.prev = this.start > 1; //1보다 크면 이전 버튼 존재

        this.next =  total > this.end * this.size; //다음 버튼 추가하는 것

    }
}
