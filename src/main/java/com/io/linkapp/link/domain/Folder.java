package com.io.linkapp.link.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.io.linkapp.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
public class Folder {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "folder_id",nullable=false)
    private UUID folderId;

    private String folderTitle;

    @OneToMany(mappedBy = "folder")
    @JsonManagedReference(value = "folder-article")
    private List<Article> articles = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "user-folder")
    private User user;

    public void setFolderTitle(String folderTitle) {
        this.folderTitle = folderTitle;
    }

    @Builder
    public Folder(User user, UUID folderId, String folderTitle){
        this.user = user;
        this.folderId = folderId;
        this.folderTitle = folderTitle;
    }

    public void changeFolderTitle(String folderTitle) {
        this.folderTitle = folderTitle;
    }
}