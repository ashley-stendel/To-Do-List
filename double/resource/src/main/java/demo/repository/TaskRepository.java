package demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import demo.domain.Task;

public interface TaskRepository extends MongoRepository<Task, String>{
	
	public List<Task> findByUsername(String username);

}
