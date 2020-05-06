package com.jasper.qrcode.exception;

public class ErrorResult {
	private String message;
	private int errorCode;
	private int status;
	
	public ErrorResult(int status, String message) {
		this(status, message, 10000);
	}
	
	public ErrorResult(int status, String message, int errorCode) {
		this.status = status;
		this.message = message;
		this.errorCode = errorCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	
}
