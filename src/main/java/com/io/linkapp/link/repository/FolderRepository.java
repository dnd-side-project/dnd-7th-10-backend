package com.io.linkapp.link.repository;

import com.io.linkapp.link.domain.Folder;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository extends JpaRepository<Folder, UUID>,
    QuerydslPredicateExecutor<Folder> {

}
