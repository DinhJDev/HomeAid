package com.homeaid.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.homeaid.models.Task;

@Repository
public interface TaskRepository extends CrudRepository <Task, Long>{
	List<Task> findAll();
	List<Task> findAllByOrderByPriorityDesc();
	List<Task> findAllByOrderByDifficultyAsc();
}
