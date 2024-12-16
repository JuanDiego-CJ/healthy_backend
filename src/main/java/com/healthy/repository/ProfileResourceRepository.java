package com.healthy.repository;

import com.healthy.model.entity.ProfileResource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileResourceRepository extends JpaRepository<ProfileResource, Integer> {
}