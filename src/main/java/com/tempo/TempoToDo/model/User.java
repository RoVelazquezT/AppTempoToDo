package com.tempo.TempoToDo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	@Column
	private String username;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false) // "google"
	private String provider;

	@Column(unique = true, nullable = false) // sub de Google
	private String providerId;

	@Column(nullable = false)
	private char status = 'A';

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime createDate;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Task> task = new ArrayList<>();

	
	public User() {
	}


	public User(Long id, String username, String email, String provider, String providerId, char status,
			LocalDateTime createDate, List<Task> task) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.provider = provider;
		this.providerId = providerId;
		this.status = status;
		this.createDate = createDate;
		this.task = task;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getProvider() {
		return provider;
	}


	public void setProvider(String provider) {
		this.provider = provider;
	}


	public String getProviderId() {
		return providerId;
	}


	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}


	public char getStatus() {
		return status;
	}


	public void setStatus(char status) {
		this.status = status;
	}


	public LocalDateTime getCreateDate() {
		return createDate;
	}


	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}


	public List<Task> getTask() {
		return task;
	}


	public void setTask(List<Task> task) {
		this.task = task;
	}

	
}