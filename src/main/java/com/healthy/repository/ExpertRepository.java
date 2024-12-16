package com.healthy.repository;

import com.healthy.model.entity.Expert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpertRepository extends JpaRepository<Expert, Integer> {
    Optional<Expert> findByFirstNameAndLastName(String firstName, String lastName);
}
