package demo.domain;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;


public class Task 
{


	private String username;
	@NotNull
	private String description; 
	private Status status;
	@NotNull
	private String name;
	
	@Id
	private String id;
	
	
//	@JsonFormat(pattern="yyyy-MM-dd")
	private Date startDate = new Date();
	
	public Task() 
	{
		
	}
	
		public Task(String username, String description, Status status, String name) {
		this.username = username;
		this.description = description;
		this.status = status;
		this.name = name;
	}
		
		public Task(String id, String username, String description, Status status, String name) {
			this.id = id;
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
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
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
	
	public enum Status
	{
		TO_DO, IN_PROGRESS, COMPLETE;
		
		/*
		 * Purpose of this is to change the output of the ENUM for displaying tasks
		 * Can be removed if do not want to see status
		 */
		//when converting to JSON object will do the following replacement
		@JsonValue 
		public String toJson() {
			//return name().toLowerCase().replaceAll("_", " ");
			return name();
		}
		
		//Create JSON from String
		@JsonCreator
		public static Status fromValue(String value)
		{
			Status status;
			switch (value) {
				case "TO_DO": status =  Status.TO_DO;
							  break;
				case "IN_PROGRESS": status = Status.IN_PROGRESS;
									break;
				case "COMPLETE": status = Status.COMPLETE;
								 break;
				default: status = Status.TO_DO;
									break;
			}
			return status;
			}
		
	}
	
	
	
}
