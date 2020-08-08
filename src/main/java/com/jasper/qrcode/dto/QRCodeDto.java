package com.jasper.qrcode.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.repository.Modifying;

public class QRCodeDto {
	@NotNull
	@Size(min = 4, max = 100)
	private String name;
	
	@NotNull
	@Size(min = 4, max = 2048)
	private String url;

	@Size(max = 2048)
	private String comment = "";

	@Size(max = 2048)
	private String address = "";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
