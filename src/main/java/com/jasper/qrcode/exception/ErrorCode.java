package com.jasper.qrcode.exception;

public enum ErrorCode {
	
	UNKNOW(10000, "Unknow"),
	
	USER_EXIST(10101, "User Exist");

	private final int value;
	private final String comment;
	
	ErrorCode(int value, String comment) {
		this.value = value;
		this.comment = comment;
	}
	
	public int value() {
		return this.value;
	}
	
	public String getComment() {
		return this.comment;
	}
}
