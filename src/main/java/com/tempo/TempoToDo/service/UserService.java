package com.tempo.TempoToDo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tempo.TempoToDo.model.User;
import com.tempo.TempoToDo.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User findOrCreateByGoogle(String email, String providerId, String name) {
		return userRepository.findByEmail(email).orElseGet(() -> {
			User u = new User();
			u.setEmail(email);
			u.setProvider("google");
			u.setProviderId(providerId);
			u.setUsername(name);
			u.setStatus('A');
			return userRepository.save(u);
		});
	}

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User save(User u) {
		return userRepository.save(u);
	}
}
