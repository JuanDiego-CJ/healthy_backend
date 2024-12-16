package com.healthy.repository;

import com.healthy.model.entity.Habit;
import com.healthy.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitRepository extends JpaRepository<Habit, Integer> {
}
