package com.io.linkapp.link.controller.predicate;

import com.io.linkapp.link.domain.QFolder;
import com.io.linkapp.link.request.FolderRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.apache.commons.lang3.StringUtils;

public class FolderFormPredicate {
    
    public static Predicate search(FolderRequest folderRequest){
        BooleanBuilder builder = new BooleanBuilder();
        QFolder qfolder = QFolder.folder;
    
        if(StringUtils.isNotEmpty(folderRequest.getFolderTitle())){
            builder.and(qfolder.folderTitle.contains(folderRequest.getFolderTitle()));
        }
        
        return builder;
    }
}
