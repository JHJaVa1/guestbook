package kr.co.jhjsoft.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@Data
@AllArgsConstructor

public class PageRequestDTO {
    //현재 페이지 번호
    private  int page;
    //페이지 당 출력 개수
    private  int size;
    //검색 항목
    private  String type;
    //검색 할 키워드
    private  String keyword;
    //매개변수가 없는 생성자 - default constructor
    public PageRequestDTO(){
        page = 1;
        size = 10;
    }

    //page와 size를 이용해서 Pageable 객체를 생성해서 리턴하는 메서드
    public Pageable getPageable(Sort sort){
        return PageRequest.of(page-1,size,sort);
    }
}
