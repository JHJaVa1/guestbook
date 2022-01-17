package kr.co.jhjsoft.guestbook.service;

import kr.co.jhjsoft.guestbook.dto.GuestBookDTO;
import kr.co.jhjsoft.guestbook.dto.PageRequestDTO;
import kr.co.jhjsoft.guestbook.dto.PageResponseDTO;
import kr.co.jhjsoft.guestbook.entity.GuestBook;
import kr.co.jhjsoft.guestbook.repository.GuestBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestBookService{
    //자동 주입받기 위해서 final로 선언해야 함
    private final GuestBookRepository repository;

    @Override
    public Long register(GuestBookDTO dto) {
        log.info(dto);
        //DTO를 Entity로 변환
        GuestBook entity = dtoToEntity(dto);
        log.info(entity);
        //데이터 삽입
        repository.save(entity);
        //삽입한 데이터의 gno 리턴
        return entity.getGno();
    }

    @Override
    public PageResponseDTO<GuestBookDTO, GuestBook> getlist(PageRequestDTO requestDTO) {
        //Pageable 객체 생성
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
        //결과를 가져오기
        Page<GuestBook> result = repository.findAll(pageable);
        //Function 생성
        Function<GuestBook, GuestBookDTO> fn = (entity -> entityToDTO(entity));
        return new PageResponseDTO<>(result, fn);
    }

    @Override
    public GuestBookDTO read(Long gno) {
        Optional<GuestBook> guestBook = repository.findById(gno);
        return guestBook.isPresent()?entityToDTO(guestBook.get()):null;
    }
}
