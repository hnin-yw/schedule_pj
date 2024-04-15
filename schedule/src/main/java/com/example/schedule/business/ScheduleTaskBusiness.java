package com.example.schedule.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.schedule.entity.*;
import com.example.schedule.service.TaskService;

@SpringBootApplication
@ComponentScan(basePackages = { "com.example.schedule.business" })
public class ScheduleTaskBusiness {
	private final TaskService taskService;

	@Autowired
	public ScheduleTaskBusiness(TaskService taskService) {
		this.taskService = taskService;
	}

	public Task saveTask(Task task) {
		return taskService.save(task);
	}

	public String updateTask(Task task) {
		Task updTask = taskService.findTaskById(task.getId());
		if (updTask == null) {
			throw new RuntimeException("Task to update doesn't exist");
		}
		updTask.setTaskTitle(task.getTaskTitle());
		taskService.save(updTask);
		return "redirect:/Tasks";
	}

	public String deleteTask(@PathVariable int id) {
		Task task = taskService.findTaskById(id);
		if (task == null) {
			throw new RuntimeException("Task Id not found");
		}
		taskService.deleteById(id);
		return "redirect:/tasks";
	}
}