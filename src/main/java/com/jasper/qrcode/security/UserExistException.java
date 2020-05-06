package com.jasper.qrcode.security;

public class UserExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 123234381984982L;
	
	public UserExistException(String message) {
	     super(message);
	 }
}
