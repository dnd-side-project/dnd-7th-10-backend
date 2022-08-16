package com.io.linkapp.link.repository;

import com.io.linkapp.link.domain.Child;
import com.io.linkapp.link.domain.Parent;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildRepository extends JpaRepository<Child, UUID> {

}
