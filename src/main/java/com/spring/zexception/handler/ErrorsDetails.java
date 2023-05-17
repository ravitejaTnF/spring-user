package com.spring.zexception.handler;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ErrorsDetails {
	private LocalDateTime timestamp;
	private String message;
	private String details;
	
	public ErrorsDetails(LocalDateTime timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public String getMessage() {
		return message;
	}
	public String getDetails() {
		return details;
	}
	
}
