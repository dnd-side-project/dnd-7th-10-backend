package com.io.linkapp.link.domain;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder
public class Market {
    
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "market_id")
    private UUID marketId;
    
    @Column(name = "market_name")
    private String marketName;
    
    @Column(name = "market_price")
    private String marketPrice;
}
