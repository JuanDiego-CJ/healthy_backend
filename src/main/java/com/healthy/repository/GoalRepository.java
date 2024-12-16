package com.healthy.repository;

import com.healthy.model.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Integer>{
    List<Goal> findByProfileId(Integer profileId);
}
