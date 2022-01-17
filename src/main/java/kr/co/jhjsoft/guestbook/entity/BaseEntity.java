package kr.co.jhjsoft.guestbook.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

//공통 속성을 만들라고 만든거지 이걸 쓰려고 만든건 아니다.
//상속을 위한 Entity Class
@MappedSuperclass
//Entity에 변화가 생기는 것을 감지하도록 설정
@EntityListeners(value = {AuditingEntityListener.class})
//get 메서드 생성
@Getter
abstract public class BaseEntity {
    //만들어진 날짜를 저장
    @CreatedDate
    //컬럼 이름은 regdate가 되고 수정은 안됨
    @Column(name = "regdate",updatable = false)
    private LocalDateTime regDate;

    //만들어진 날짜를 저장
    @LastModifiedDate
    //컬럼 이름은 moddate
    @Column(name = "moddate")
    private LocalDateTime modDate;
}
