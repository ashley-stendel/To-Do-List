package demo.domain;

import java.util.UUID;

public class Message {
	private String id = UUID.randomUUID().toString();
	private String content;

	public Message() {
	}

	public Message(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
}