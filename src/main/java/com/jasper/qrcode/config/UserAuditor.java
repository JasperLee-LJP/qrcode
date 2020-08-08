package com.jasper.qrcode.config;

import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import com.jasper.qrcode.security.SecurityUtils;

@Configuration
public class UserAuditor implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		return SecurityUtils.getCurrentUsername();
	}

}
