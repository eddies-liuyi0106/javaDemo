package net.canway.hotel_message;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("net.canway.hotel_message")
@MapperScan("net.canway.hotel_message.dao")
public class HotelMessageApplication {
    public static void main(String[] args) {
        SpringApplication.run(HotelMessageApplication.class, args);
    }
}
