package com.example.demo;

import java.util.Random;

public class Greeting {

	private final long id;
	private final String content;
	private final String message;
	private int rand;
	
	public Greeting(long id, String content, String message) {
		this.id = id;
		Random r = new Random();
		this.rand = r.nextInt();
		this.message = message;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
	
	public String getMessage() {
		return message;
	}
}