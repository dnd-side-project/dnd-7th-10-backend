package com.io.linkapp.link.repository;

import com.io.linkapp.link.domain.Inquiry;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, UUID>,
    QuerydslPredicateExecutor<Inquiry> {
    
    Inquiry findInquiryById(UUID id);
    
}
