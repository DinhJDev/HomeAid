package com.homeaid.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homeaid.models.Task;
import com.homeaid.repositories.HouseholdRepository;
import com.homeaid.repositories.TaskRepository;

@Service
public class TaskService {
	@Autowired
	private HouseholdRepository houseRepo;
	
	
	private TaskRepository taskRepository; 
	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}
	
	
	public List<Task> allTask(){
		return this.taskRepository.findAll();
	}
	
	public Task createAssignee(Long id) {
		return this.taskRepository.save(id);
	}
	
	public Task updateTask(Long id, Task task) {
		return this.taskRepository.save(task);
	}
	
	public String removeTask(Long id) {
		this.houseRepo.deleteById(id);
		return "Selected Task" + id + "is deleted";
	}
	
}
