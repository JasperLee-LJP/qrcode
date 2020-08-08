package com.jasper.qrcode.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jasper.qrcode.dto.QRCodeDto;
import com.jasper.qrcode.exception.CommonException;
import com.jasper.qrcode.exception.ErrorCode;
import com.jasper.qrcode.model.QRCode;
import com.jasper.qrcode.security.SecurityUtils;
import com.jasper.qrcode.service.QRCodeService;

@RestController
@RequestMapping("/api")
public class QRCodeController {
	private final QRCodeService qrCodeService;

	public QRCodeController(QRCodeService qrCodeService) {
		this.qrCodeService = qrCodeService;
	}
	
	@GetMapping("/status")
	public ResponseEntity<Boolean> getStatus() {
		return new ResponseEntity<Boolean>(true, null, HttpStatus.OK);
	}

	@PostMapping("/qrcode")
	public ResponseEntity<QRCode> createQRCode(@Valid @RequestBody QRCodeDto qrCodeDto) {
		QRCode qrCode = new QRCode();
		qrCode.setAddress(qrCodeDto.getAddress());
		qrCode.setComment(qrCodeDto.getComment());
		qrCode.setName(qrCodeDto.getName());
		qrCode.setUrl(qrCodeDto.getUrl());

		qrCode = qrCodeService.saveQRCode(qrCode);
		return new ResponseEntity<QRCode>(qrCode, null, HttpStatus.OK);
	}

	@GetMapping("/qrcodes")
	public ResponseEntity<List<QRCode>> getQRCodes(
			@RequestParam(value =  "name", required = false, defaultValue = "") String name,
			@RequestParam(name = "url", required = false, defaultValue = "") String url,
			@RequestParam(name = "address", required = false, defaultValue = "") String address) {
		name = "%" + name + "%";
		url = "%" + url + "%";
		address = "%" + address + "%";
		Optional<String> usernameOptional = SecurityUtils.getCurrentUsername();
		if (!usernameOptional.isPresent()) {
			throw new CommonException(HttpStatus.UNAUTHORIZED, ErrorCode.USER_EXIST);
		}
		List<QRCode> qrCodes = qrCodeService.searchQRCodes(usernameOptional.get(), name, url, address);
		return new ResponseEntity<List<QRCode>>(qrCodes, null, HttpStatus.OK);
	}

	@GetMapping("/qrcode/scan/{id}")
	public void scanQRCode(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
		Optional<QRCode> qRCOpt = qrCodeService.findQRCode(id);
		if (!qRCOpt.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		QRCode qrCode = qRCOpt.get();
		qrCode.setScanCount(qrCode.getScanCount() + 1);
		qrCode = qrCodeService.saveQRCode(qrCode);
		response.sendRedirect(qRCOpt.get().getUrl());
	}

	@PutMapping("/qrcode/{id}")
	public ResponseEntity<QRCode> changeQRCode(@PathVariable("id") Long id, @Valid @RequestBody QRCodeDto qrCodeDto) {
		Optional<QRCode> qRCOptional = qrCodeService.findQRCode(id);
		if (!qRCOptional.isPresent()) {
			throw new CommonException(HttpStatus.NOT_FOUND, ErrorCode.INVALID_DATA);
		}
		
		QRCode qrCode = qRCOptional.get();
		qrCode.setName(qrCodeDto.getName());
		qrCode.setUrl(qrCodeDto.getUrl());
		qrCode.setAddress(qrCodeDto.getAddress());
		qrCode.setComment(qrCodeDto.getComment());
		qrCode = qrCodeService.saveQRCode(qrCode);
		
		return new ResponseEntity<QRCode>(qrCode, null, HttpStatus.OK);
	}

	@DeleteMapping("/qrcode/{id}")
	public ResponseEntity<Void> deleteQRCode(@PathVariable("id") Long id) {
		qrCodeService.deleteQRCode(id);
		return new ResponseEntity<Void>(null, null, HttpStatus.OK);
	}
}
