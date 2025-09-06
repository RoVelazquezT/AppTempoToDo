package com.tempo.TempoToDo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tempo.TempoToDo.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);
	Optional<User> findByProviderId(String providerId);
}
