package com.tempo.TempoToDo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tempo.TempoToDo.model.Task;
import com.tempo.TempoToDo.model.User;

public interface TaskRepository extends JpaRepository<Task, Long> {
	  List<Task> findByUserOrderByCreateDateDesc(User  user);
	  List<Task> findByUserAndCompletedFalseOrderByCreateDateDesc(User user);
	  List<Task> findByUserAndCompletedTrueOrderByCreateDateDesc(User user);

	}
