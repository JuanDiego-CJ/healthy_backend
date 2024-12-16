package com.healthy.repository;

import com.healthy.model.entity.HabitType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HabitTypeRepository extends JpaRepository<HabitType, Integer> {
    Optional<HabitType> findByName(String name);
}
