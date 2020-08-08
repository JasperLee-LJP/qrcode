package com.jasper.qrcode.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jasper.qrcode.model.QRCode;
import com.jasper.qrcode.repository.QRCodeRepository;


@Service
@Transactional
public class QRCodeService {
	private final QRCodeRepository qrCodeRepository;

	public QRCodeService(QRCodeRepository qrCodeRepository) {
		this.qrCodeRepository = qrCodeRepository;
	}

	public QRCode saveQRCode(QRCode qrCode) {
		return qrCodeRepository.save(qrCode);
	}
	
	public List<QRCode> getQRCodes(String username) {
		return qrCodeRepository.findByCreatedBy(username);
	}
	
	public List<QRCode> searchQRCodes(String username, String name, String url, String address) {
		return qrCodeRepository.findByCreatedByAndNameLikeAndUrlLikeAndAddressLike(username, name, url, address);
	}
	
	public Optional<QRCode> findQRCode(Long id) {
		return qrCodeRepository.findById(id);
	}
	
	public void deleteQRCode(Long id) {
		qrCodeRepository.deleteById(id);
	}
}
