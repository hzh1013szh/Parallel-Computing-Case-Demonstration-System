package com.hwk.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.hwk.springboot.dao")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
/*
CREATE TABLE huangzihao."user"(
	userid char(20) NOT NULL UNIQUE PRIMARY KEY,
	password char(20) NOT NULL,
	type integer(1) NOT NULL,
	phone char(16) NOT NULL)
TABLESPACE pg_default;
COMMENT ON COLUMN huangzihao."user".type IS '0代表开发，1代表企业，2代表管理员';
 */