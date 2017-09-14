package demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import demo.domain.Task;
import demo.repository.TaskRepository;

@RestController
public class TaskController {
	
	@Autowired
	private TaskRepository taskRepository;
	
	@RequestMapping(value = "/task", method = RequestMethod.POST)
	public Task addTask(@RequestBody Task task, Principal principal)
	{
		taskRepository.save(task);
		
		return task;
	}
	
	@RequestMapping("/alltasks")
	public List<Task> getAllTasks(@RequestBody String username, Principal principal)
	{
		System.out.println(username);
		return taskRepository.findByUsername(username);
	}

}
