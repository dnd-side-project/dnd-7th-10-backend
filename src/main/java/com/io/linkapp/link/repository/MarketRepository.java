package com.io.linkapp.link.repository;

import com.io.linkapp.link.domain.Market;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketRepository extends JpaRepository<Market, UUID> {

}
