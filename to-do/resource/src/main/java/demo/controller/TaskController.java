package demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.domain.Task;
import demo.domain.Task.Status;
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
	
	@RequestMapping("/allstatuses")
	public Status[] allStatus()
	{
		return Status.values();
	}
	
	@RequestMapping("/alltasks")
	public List<Task> getAllTasks(@RequestBody String username, Principal principal)
	{
		System.out.println(username);
		return taskRepository.findByUsername(username);
	}
	
	@PostMapping("/deletetask")
	public int deleteTask(@RequestBody String id)
	{
		
		taskRepository.deleteById(id);
		
		return 0;
	}
	
	@RequestMapping("/changestatus")
	public int changeStatus(@RequestParam String taskId, @RequestParam Status newStatus)
	{
		
		taskRepository.updateStatus(taskId, newStatus);
		
		return 0;
	}
	
	
	@PostMapping("/edittask")
	public int editTask(@RequestBody Task newTask)
	{
		taskRepository.save(newTask);
		return 0;
	}

}
