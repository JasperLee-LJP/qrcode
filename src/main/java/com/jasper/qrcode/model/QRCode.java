package com.jasper.qrcode.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "QRCODE")
public class QRCode {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QRCODE_SEQ")
//	@SequenceGenerator(name = "QRCODE_SEQ", sequenceName = "QRCODE_SEQ", allocationSize = 1, initialValue = 1)
	private Long id;

	@Column(name = "NAME", length = 100)
	@NotNull
	@Size(min = 4, max = 100)
	private String name;

	@Column(name = "URL", length = 2048)
	@NotNull
	@Size(min = 4, max = 2048)
	private String url;

	@Column(name = "COMMENT", length = 2048)
	@NotNull
	@Size(min = 0, max = 2048)
	private String comment;

	@Column(name = "ADDRESS", length = 2048)
	@NotNull
	@Size(min = 0, max = 2048)
	private String address;

	@Column(name = "SCAN_COUNT", length = 2048)
	private int scanCount;
	
	@CreatedBy
	@Column(name = "CREATED_BY", updatable = false, nullable = false)
	private String createdBy;

	@CreatedDate
	@Column(name = "CREATEED_TIME", updatable = false, nullable = false)
	private Date createdDate;

	@LastModifiedDate
	@Column(name = "UPDATE_TIME", nullable = false)
	private Date modifiedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public int getScanCount() {
		return scanCount;
	}

	public void setScanCount(int scanCount) {
		this.scanCount = scanCount;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		QRCode code = (QRCode) o;
		return id.equals(code.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "QRCode{" + "name='" + name + '\'' + ", url='" + url + '\'' + ", comment='" + comment + '\''
				+ ", address='" + address + '\'' + ", scanCount='" + scanCount + '\'' + ", id=" + id + '}';
	}
}
