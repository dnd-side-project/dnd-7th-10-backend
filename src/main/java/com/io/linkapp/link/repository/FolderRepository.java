package com.io.linkapp.link.repository;

import com.io.linkapp.link.domain.Folder;
import com.io.linkapp.user.domain.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

public interface FolderRepository extends JpaRepository<Folder, UUID>{
    List<Folder> findByUser(User user);
}
