package com.tempo.TempoToDo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tempo.TempoToDo.model.Task;
import com.tempo.TempoToDo.model.User;
import com.tempo.TempoToDo.service.TaskService;
import com.tempo.TempoToDo.service.UserService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TempoController {
	private final TaskService taskService;
	private final UserService userService;

	public TempoController(TaskService taskService, UserService userService) {
		this.taskService = taskService;
		this.userService = userService;
	}

	@GetMapping("/tempoLogin")
	public String login() {
		return "tempoLogin";
	}

	@GetMapping("/tempoHome")
	public String home(@AuthenticationPrincipal OAuth2User oauthUser, Model model) {

		String name = oauthUser.getAttribute("name");
		String email = oauthUser.getAttribute("email");

		User user = userService.findByEmail(email).orElse(null);

		List<Task> tasks = (user != null) ? taskService.findAllByUser(user) : List.of();
		List<Task> pendingTasks = taskService.findPendingByUser(user);
		List<Task> completedTasks = taskService.findCompletedByUser(user);

		model.addAttribute("pending", pendingTasks);
		model.addAttribute("completed", completedTasks);
		model.addAttribute("user", user);
		model.addAttribute("name", name);
		model.addAttribute("tasks", tasks);
		model.addAttribute("newTask", new Task()); // para el form inline si querés
		return "tempoHome";
	}

	@GetMapping("/create-task")
	public String createTask(@AuthenticationPrincipal OAuth2User oauthUser, Model model) {
		String name = oauthUser.getAttribute("name");
		String email = oauthUser.getAttribute("email");

		User user = userService.findByEmail(email).orElse(null);

		model.addAttribute("user", user);
		model.addAttribute("name", name);
		model.addAttribute("task", new Task());
		return "create-task";
	}

	@PostMapping("/save-task")
	public String saveTask(@RequestParam String title, @RequestParam(required = false) String description,
			@AuthenticationPrincipal OAuth2User oauth2User) {
		String email = oauth2User.getAttribute("email");
		taskService.saveTask(title, description, email);
		return "redirect:/tempoHome";
	}

	// Task Pendientes
	@PostMapping("/tasks/{id}/complete")
	public ResponseEntity<Void> completeTask(@PathVariable Long id) {
		// lógica para completar la tarea
		Task task = taskService.findById(id).orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

		task.setCompleted(true);
		taskService.save(task);

		System.out.println("Task marcada como completada : " + id);
		return ResponseEntity.ok().build();
	}

	// Update Task
	@PutMapping("/tasks/{id}")
	public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
		Task task = taskService.findById(id).orElse(null);
		if (task == null) {
			throw new RuntimeException("Tarea no encontrada");
		}
		task.setTitle(updatedTask.getTitle());
		task.setDescription(updatedTask.getDescription());

		taskService.save(task);

		System.out.println("Task Actualizada : " + id);

		return ResponseEntity.ok(task);
	}

//DELETE de una tarea
	@PostMapping("tasks/{id}/delete")
	@ResponseBody
	public ResponseEntity<String> deleteTask(@PathVariable Long id) {
		System.out.println("Task Eliminada : " + id);
		try {
			taskService.delete(id);
			return ResponseEntity.ok("Tarea eliminada con éxito");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la tarea");
		}
	}

	@PostMapping("/logout")
	public String logout() {
		return "tempoLogin";
	}

}
