package kr.co.jhjsoft.guestbook.service;

import com.querydsl.core.BooleanBuilder;
import javassist.compiler.ast.Keyword;
import kr.co.jhjsoft.guestbook.dto.GuestBookDTO;
import kr.co.jhjsoft.guestbook.dto.PageRequestDTO;
import kr.co.jhjsoft.guestbook.dto.PageResponseDTO;
import kr.co.jhjsoft.guestbook.entity.GuestBook;
import kr.co.jhjsoft.guestbook.entity.QGuestBook;
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
        //Page<GuestBook> result = repository.findAll(pageable);
        BooleanBuilder booleanBuilder = getSearch(requestDTO);
        Page<GuestBook> result = repository.findAll(booleanBuilder,pageable);
        //Function 생성
        Function<GuestBook, GuestBookDTO> fn = (entity -> entityToDTO(entity));
        return new PageResponseDTO<>(result, fn);
    }

    @Override
    public GuestBookDTO read(Long gno) {
        Optional<GuestBook> guestBook = repository.findById(gno);
        return guestBook.isPresent()?entityToDTO(guestBook.get()):null;
    }

    @Override
    public void modify(GuestBookDTO dto) {
        //수정할 데이터를 찾아오기
        Optional<GuestBook> result = repository.findById(dto.getGno());
        if(result.isPresent()){
            GuestBook entity = result.get();
            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());
            repository.save(entity);

        }
    }

    @Override
    public void remove(Long gno) {
        Optional<GuestBook> result = repository.findById(gno);
        if(result.isPresent()){
            repository.deleteById(gno);
        }
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO){
        String type = requestDTO.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGuestBook qGuestBook = QGuestBook.guestBook;
        String keyword = requestDTO.getKeyword();

        if(type == null || type.trim().length() == 0){
            return booleanBuilder;
        }
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        //t c w 는 검색 화면에서 select의 option들의 value가 되어야 한다

        if (type.contains("t")){
            conditionBuilder.or(qGuestBook.title.contains(keyword));
        }
        if (type.contains("c")){
            conditionBuilder.or(qGuestBook.content.contains(keyword));
        }
        if (type.contains("w")){
            conditionBuilder.or(qGuestBook.writer.contains(keyword));
        }
        //모든 조건 통합
        booleanBuilder.and(conditionBuilder);
        return booleanBuilder;
    }
}
