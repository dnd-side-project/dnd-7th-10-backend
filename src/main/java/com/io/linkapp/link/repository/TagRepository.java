package com.io.linkapp.link.repository;

import com.io.linkapp.link.domain.Tag;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID>, QuerydslPredicateExecutor<Tag> {

}
