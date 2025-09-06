package com.tempo.TempoToDo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tempo.TempoToDo.model.Task;
import com.tempo.TempoToDo.model.User;
import com.tempo.TempoToDo.repository.TaskRepository;

@Service
public class TaskService {

	
	private final TaskRepository taskRepository;
	private final UserService userService; 
	
	public TaskService(TaskRepository taskRepository, UserService userService) {
		this.taskRepository = taskRepository;
		this.userService = userService; 
	}

	public List<Task> findAllByUser(User user) {
		return taskRepository.findByUserOrderByCreateDateDesc(user);
	}

	public Task saveTask(String title, String description, String userEmail) {
		User user = userService.findByEmail(userEmail)
				.orElseThrow(() -> new IllegalStateException("Usuario no encontrado: " + userEmail));
		Task task = new Task();
		task.setTitle(title);
		task.setDescription(description);
		task.setUser(user);
		return taskRepository.save(task);
	}

	public void delete(Long id) {
		taskRepository.deleteById(id);
	}
	
	public List<Task> findPendingByUser(User user) {
	    return taskRepository.findByUserAndCompletedFalseOrderByCreateDateDesc(user);
	}

	public List<Task> findCompletedByUser(User user) {
	    return taskRepository.findByUserAndCompletedTrueOrderByCreateDateDesc(user);
	}

	public Optional<Task> findById(Long id) {
	    return taskRepository.findById(id);
	}
	
	public Task save(Task task) {
	    return taskRepository.save(task);
	}
	
}
