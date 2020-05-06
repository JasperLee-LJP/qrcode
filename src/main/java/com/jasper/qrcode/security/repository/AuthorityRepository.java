package com.jasper.qrcode.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jasper.qrcode.security.model.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
