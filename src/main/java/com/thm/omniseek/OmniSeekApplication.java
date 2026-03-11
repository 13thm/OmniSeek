package com.thm.omniseek;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.thm.omniseek.mapper")
public class OmniSeekApplication {

    public static void main(String[] args) {
        SpringApplication.run(OmniSeekApplication.class, args);
    }

}
