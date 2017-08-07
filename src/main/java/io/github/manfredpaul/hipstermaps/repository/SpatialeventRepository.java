package io.github.manfredpaul.hipstermaps.repository;

import io.github.manfredpaul.hipstermaps.domain.Spatialevent;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Spatialevent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpatialeventRepository extends JpaRepository<Spatialevent,Long> {
    
}
