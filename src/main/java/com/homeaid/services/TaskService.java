package com.homeaid.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homeaid.models.Member;
import com.homeaid.models.Task;
import com.homeaid.repositories.TaskRepository;

@Service
public class TaskService {
	@Autowired
	TaskRepository taskRepository;
	
	
	public List<Task> allTask(){
		return this.taskRepository.findAll();
	}
	

	public List<Task> descendingPriorityTasks(){
		return this.taskRepository.findAllByOrderByPriorityDesc();
	}
	
	public List<Task> ascendingDifficultyTasks() {
		return this.taskRepository.findAllByOrderByDifficultyAsc();
	}
	
	
	public Task createTask(Task task) {
		return this.taskRepository.save(task);
	}
	
	// Read - added by mabel
	public Task getOneTask(Long id) {
		return this.taskRepository.findById(id).orElse(null);
	}
	
	public Task updateTask(Long id, Task task) {
		return this.taskRepository.save(task);
	}
	
	public String removeTask(Long id) {
		this.taskRepository.deleteById(id);
		return "Selected Task" + id + "is deleted";
	}
	public void addAssignee(Member assignee, Task task) {
		List<Member> currentAssignees = task.getAssignees();
		currentAssignees.add(assignee);
		this.taskRepository.save(task);
	}
	public void removeAssignee(Member assignee, Task task) {
		List<Member> currentAssignees = task.getAssignees();
		currentAssignees.remove(assignee);
		this.taskRepository.save(task);
	}
	
	
}
