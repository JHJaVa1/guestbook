package kr.co.jhjsoft.guestbook;

import kr.co.jhjsoft.guestbook.dto.GuestBookDTO;
import kr.co.jhjsoft.guestbook.dto.PageRequestDTO;
import kr.co.jhjsoft.guestbook.dto.PageResponseDTO;
import kr.co.jhjsoft.guestbook.entity.GuestBook;
import kr.co.jhjsoft.guestbook.service.GuestBookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.bind.SchemaOutputResolver;

@SpringBootTest
public class GuestBookServiceTest {
    @Autowired
    private GuestBookService service;

    //@Test
    public  void registerTest(){
        GuestBookDTO dto = GuestBookDTO.builder().title("제목").content("내용").writer("효재").build();
        Long gno = service.register(dto);
        System.out.println(gno);
    }
    @Test
    public  void listTest(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).type("w").keyword("효재").build();
        PageResponseDTO<GuestBookDTO, GuestBook> pageResponseDTO = service.getlist(pageRequestDTO);
        for(GuestBookDTO dto : pageResponseDTO.getDtoList()){
            System.out.println(dto);
        }
        //이전과 다음 링크 여부와 전체 페이지 개수 확인
        System.out.println("=============================");
        System.out.println("이전 : " + pageResponseDTO.isPrev());
        System.out.println("이전 : " + pageResponseDTO.isNext());
        System.out.println("이전 : " + pageResponseDTO.getTotalPage());
        //페이지 번호 목록 출력
        System.out.println("=============================");
        for(Integer i : pageResponseDTO.getPageList()){
            System.out.println(i + "\t");
        }


    }


}
