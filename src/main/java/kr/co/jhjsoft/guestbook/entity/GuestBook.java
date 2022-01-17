package kr.co.jhjsoft.guestbook.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
//만들 때 빌더 메시지를 쓸 수 있는 것
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GuestBook extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gno;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String content;

    @Column(length = 100, nullable = false)
    private String writer;

    //title 수정 메서드 - setter
    public void changeTitle(String title){
        this.title = title;
    }

    //content 수정 메서드
    public void changeContent(String content){
        this.content = content;
    }
}
