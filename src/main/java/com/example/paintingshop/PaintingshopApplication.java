package com.example.paintingshop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.paintingshop.mapper")
public class PaintingshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaintingshopApplication.class, args);
    }

}
