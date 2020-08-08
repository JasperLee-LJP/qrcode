package com.jasper.qrcode.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jasper.qrcode.model.QRCode;


public interface QRCodeRepository extends JpaRepository<QRCode, Long> {
	List<QRCode> findByCreatedBy(String username);
	
	List<QRCode> findByCreatedByAndNameLikeAndUrlLikeAndAddressLike(String username, String name, String url, String address);
}
