package com.healthy.repository;

import com.healthy.model.entity.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Integer> {
    Page<Plan> findPlanByProfileId (Integer profileId , Pageable pageable);
}