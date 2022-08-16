package com.io.linkapp.link.domain;

import com.io.linkapp.exception.CustomGlobalException;
import com.io.linkapp.exception.ErrorCode;
import com.io.linkapp.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Remind {
    
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "remind_id",nullable=false)
    private UUID remindId;
    
    @Column(name = "remind_title",nullable=false)
    private String remindTitle;
    
    
    @OneToOne(fetch = FetchType.LAZY)
    //name : 매핑할 외래키 이름. 즉 현재 해당 엔티티의 조인 기준이 되는 컬럼명 . 만약 해당되는 컬럼이 없다면 spring에서 자동으로 생성해줌 - 아래 아티클 쪽 설명 참고
    @JoinColumn(name = "user_id") // referencedColumnName 을 생략하면 조인 기준을 대상 테이블의 pk로 자동 지정한다.
    private User user;
    
    
    //https://www.inflearn.com/questions/480533 - 완전 좋음. 특히 하단의 Child 클래스 나오는 질문
    //https://www.inflearn.com/questions/113969
    
    //데이터베이스에서 외래키는 다(N)에 생성됩니다.
    //리마인드(1)의 입장에서 멤버를 참조하기 위해 외래키를 매핑하려면 무조건 데이터베이스 상에서 아티클(N)테이블에 생성된 외래키 칼럼을 바라볼 수 밖에 없습니다.
    // 외래키는 다(N)에 생성되기 때문입니다.
    @OneToMany(fetch = FetchType.LAZY) //미리 가져오는 것이 아니라 요청하는 순간 가져온다
    @JoinColumn(name = "remind_id",referencedColumnName="remind_id") //, referencedColumnName 을 생략하면 조인 기준을 대상 테이블의 pk로 자동 지정한다.
    // 나는 현재 article_id로 조인하는 것이 아니므로 referenced를 remind_id로 지정해줌
    private List<Article> articleList = new ArrayList<>(); //북마크 된 것만 최종 결과로 보내줘야 함
    //name : 매핑할 외래키 이름. 즉 현재 해당 엔티티의 조인 기준이 되는 컬럼명 . 만약 해당되는 컬럼이 없다면 spring에서 자동으로 생성해줌
    // child 쪽(다)에 parent_id 컬럼(일)이 없어도 @JoinColumn(name = "parent_id")라고 되어 있다면 child 테이블에 parent_id라는 fk칼럼이 생깁니다.
    // - 위의 레퍼런스에 나와있음
    //referencedColumnName : 쉽게 말해 반대편 테이블의 조인 기준이 되는 컬럼명
    
    //메모는 어차피 별개니까 그냥 프론트 측에서 메모 최신순으로 전체 조회만 가져오면 될 듯
    //to - do: 알람 정보는 푸시 쪽과 함께 작업
    
    //private Integer hour;
    //private Integer min;
    
    
}
