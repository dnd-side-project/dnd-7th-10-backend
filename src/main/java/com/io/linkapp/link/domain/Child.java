package com.io.linkapp.link.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder
public class Child {
    
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "child_id")
    private UUID id;
    
    @Column(name = "child_name")
    private String childName;
    
    @Column(name = "parent_id")
    private UUID parentId;
    
    private boolean isBookmark = false;
    
    
    public void setBookmark(boolean isBookmark){
        this.isBookmark = isBookmark;
    }
    
    public void setParentId(UUID id){
        this.parentId = id;
    }
    
    //현재 조인이 되었기 때문에 parentId값만 있으면 추가시 remind쪽에서 바로 가져올 수 있음
    // 그렇다면 bookmark가 true이면 parentId값이 있고, flase면 parentId값을 null로 하면 되지 않을까
    

}
