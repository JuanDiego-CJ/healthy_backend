package com.healthy.repository;

import com.healthy.model.entity.Profile;
import com.healthy.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    Optional<Profile> findByUser(Optional<User> user);
}
