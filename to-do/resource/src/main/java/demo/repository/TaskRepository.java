package demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import demo.domain.Task;

public interface TaskRepository extends MongoRepository<Task, String>, TaskRepositoryCustom{
	
	public List<Task> findByUsername(String username);
	
	public Task findById(String id);
	
	//@Transactional
	public void deleteById(String id);

}
