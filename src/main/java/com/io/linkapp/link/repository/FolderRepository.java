package com.io.linkapp.link.repository;

import com.io.linkapp.link.domain.Folder;
import com.io.linkapp.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FolderRepository extends JpaRepository<Folder, UUID>{
    List<Folder> findByUser(User user);
}
