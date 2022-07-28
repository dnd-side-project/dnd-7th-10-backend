package com.io.linkapp.link.repository;

import com.io.linkapp.link.domain.Memo;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, UUID> {
    
}
