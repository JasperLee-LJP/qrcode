package com.jasper.qrcode.exception;

import org.springframework.http.HttpStatus;

public class CommonException extends RuntimeException {
	private static final long serialVersionUID = 123234381984982L;
	
	private HttpStatus status;
	private ErrorCode errorCode;
	
	public CommonException(HttpStatus status) {
		this(status, ErrorCode.UNKNOW);
	}
	
	public CommonException(HttpStatus status, ErrorCode errorCode) {
		this.status = status;
		this.errorCode = errorCode;
	}
	
	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
