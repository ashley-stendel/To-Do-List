package demo.domain;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


public class Task 
{


	private String username;
	private String description; 
	private String status;
	private String name;
	
	@Id
	private String id;
	private Date startDate = new Date();
	
	public Task() 
	{
		
	}
	
	public Task(String username, String description, String status, String name) {
		this.username = username;
		this.description = description;
		this.status = status;
		this.name = name;
	}	
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Override
	public String toString() {
		return "Task [username=" + username + ", description=" + description + ", status=" + status + ", name=" + name
				+ ", id=" + id + ", startDate=" + startDate + "]";
	}
	
	
	
}
