package com.io.linkapp.link.repository;

import com.io.linkapp.link.domain.Article;
import com.io.linkapp.link.domain.QArticle;
import com.io.linkapp.link.domain.QRemind;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class RemindRepositorySupport extends QuerydslRepositorySupport {
    
    public RemindRepositorySupport() {
        super(QuerydslRepositorySupport.class);
    }
    
  // 여기서 해주는 게 아니라 애초에 remind의 아티클 리스트에 추가될 때 북마크가 true인 애들만 나오게 해야 함
    //그리고 북마크 해제되면 아티클 리스트에도 제외되도록
}
