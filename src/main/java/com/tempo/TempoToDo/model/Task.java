package com.tempo.TempoToDo.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tasks")

public class Task {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;

	  @Column(nullable=false)
	  private String title;

	  @Column(length=2000)
	  private String description;

	  @CreationTimestamp
	  @Column(nullable=false, updatable=false)
	  private LocalDateTime createDate;

	  @UpdateTimestamp
	  private LocalDateTime updateDate;

	  @Column(nullable=false)
	  private boolean completed = false;

	  @ManyToOne(fetch = FetchType.LAZY, optional=false)
	  @JoinColumn(name = "user_id", nullable=false)
	  private User user;


	public Task() {
	}
	
	public Task(Long id, String title, String description, LocalDateTime createDate, LocalDateTime updateDate,
			boolean completed, User user) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.completed = completed;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}