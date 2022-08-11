package com.io.linkapp.link.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

    public void setFolderTitle(String folderTitle) {
        this.folderTitle = folderTitle;
    }

    @Builder
    public Folder(UUID folderId, String folderTitle){
        this.folderId = folderId;
        this.folderTitle = folderTitle;
    }
}