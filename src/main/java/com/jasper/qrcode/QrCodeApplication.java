package com.jasper.qrcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class QrCodeApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(QrCodeApplication.class, args);
    }

}
