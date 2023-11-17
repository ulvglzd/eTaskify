package com.etaskify.etaskifybackend.repository;

import com.etaskify.etaskifybackend.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    boolean existsByName(String name);
}
