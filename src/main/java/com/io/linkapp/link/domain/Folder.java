package com.io.linkapp.link.domain;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Folder")
@Data
@NoArgsConstructor
public class Folder {
    
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "folder_id")
    private UUID folderId;
    
    @Column(name = "folder_title")
    private String folderTitle;
    
    @Builder
    public Folder(UUID folderId, String folderTitle){
        this.folderId = folderId;
        this.folderTitle = folderTitle;
    }
    
}
