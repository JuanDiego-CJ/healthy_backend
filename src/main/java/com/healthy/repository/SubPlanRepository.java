package com.healthy.repository;

import com.healthy.model.entity.SubPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubPlanRepository extends JpaRepository<SubPlan, Integer> {
    Optional<SubPlan> findByName(String name);
}