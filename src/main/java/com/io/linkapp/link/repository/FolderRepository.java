package com.io.linkapp.link.repository;

import com.io.linkapp.link.domain.Folder;
import com.io.linkapp.user.domain.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FolderRepository extends JpaRepository<Folder, UUID>{

    @Query("SELECT folder FROM Folder folder join fetch folder.articles")
    List<Folder> findByUser(User user);
}
