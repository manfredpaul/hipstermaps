package io.github.manfredpaul.hipstermaps.repository;

import io.github.manfredpaul.hipstermaps.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
