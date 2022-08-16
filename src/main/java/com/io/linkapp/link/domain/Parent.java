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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Parent {
    
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "parent_id",nullable=false)
    private UUID parentId;
    
    @Column(name = "parent_name",nullable=false)
    private String parentName;
    
    @OneToMany(fetch = FetchType.LAZY) //미리 가져오는 것이 아니라 요청하는 순간 가져온다
    @JoinColumn(name = "parent_id",referencedColumnName="parent_id")
    private List<Child> childList = new ArrayList<>();
   

    
}
