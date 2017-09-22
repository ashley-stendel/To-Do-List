package demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import demo.domain.Task;
import demo.domain.Task.Status;

public class TaskRepositoryImpl implements TaskRepositoryCustom{
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	public int updateStatus(String taskId, Status status)
	{
		Criteria criteria = Criteria.where("id").is(taskId);
		Update update = new Update();
		update.set("status", status);
		mongoTemplate.updateFirst(Query.query(criteria), update, Task.class);
		
		return 0;
	}

}
