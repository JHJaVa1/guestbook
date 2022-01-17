package kr.co.jhjsoft.guestbook.controller;

import kr.co.jhjsoft.guestbook.dto.GuestBookDTO;
import kr.co.jhjsoft.guestbook.dto.PageRequestDTO;
import kr.co.jhjsoft.guestbook.dto.PageResponseDTO;
import kr.co.jhjsoft.guestbook.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//로그 출력을 위한 어노테이션
@Log4j2
//PageController를 만들기 위한 어노테이션
@Controller
@RequiredArgsConstructor
public class GuestBookController {
    private final GuestBookService service;
    @GetMapping("/")
    public String main(){
        //println을 쓰면 배포할 때 지워야 하지만 얘는 안지워도 된다.
        log.info("시작 요청");
        //templates에 있는 guestbook 디렉토리의 list.html을 출력
        return "redirect:/guestbook/list";
    }
    @GetMapping("/guestbook/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        log.info("목록 보기 ......");
        PageResponseDTO result = service.getlist(pageRequestDTO);
        model.addAttribute("result",result);
    }
    @GetMapping("/guestbook/register")
    public void register(){
        log.info("삽입 요청 페이지로 이동");
    }
    @PostMapping("/guestbook/register")
    public String register(GuestBookDTO dto, RedirectAttributes redirectAttributes){
        log.info("삽입 처리");
        //삽입 처리
        Long gno = service.register(dto);
        //리다이렉트 할 때 한번만 사용하는 데이터 생성
        redirectAttributes.addFlashAttribute("msg",gno + "삽입");
        return "redirect:/guestbook/list";

    }

    @GetMapping("/guestbook/read")
    //파라미터 중에서 gno는 gno에 대입되고
    //나머지는 requestDTO에 대입 된다. 다음 결과 페이지에 전송된다.
    public void read(Long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        GuestBookDTO dto = service.read(gno);
        model.addAttribute("dto",dto);
    }

}
