package com.etaskify.etaskifybackend.repository;

import com.etaskify.etaskifybackend.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    boolean existsByName(String name);

    @Query("SELECT u.organization FROM User u WHERE u.email = :email")
    Optional<Organization> findByUsersUserEmail(@Param("email") String email);

}
