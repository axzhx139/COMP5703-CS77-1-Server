package com.gday.trackmygrocery;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan("com.gday.trackmygrocery.mapper")
@SpringBootApplication
@EnableSwagger2
public class TrackmygroceryApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrackmygroceryApplication.class, args);
    }

}
