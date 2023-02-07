package com.ilzf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.**.mapper")
public class ReaderApplication {

	public static void main(String[] args) {
		long s = System.currentTimeMillis();
		SpringApplication.run(ReaderApplication.class, args);
		long e = System.currentTimeMillis();
		System.out.print("启动耗时:" + (e - s) / 1000 + "秒");
	}

}
