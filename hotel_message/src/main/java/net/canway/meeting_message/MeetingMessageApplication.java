package net.canway.meeting_message;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("net.canway.meeting_message")
@MapperScan("net.canway.meeting_message.mapper")
public class MeetingMessageApplication {
    public static void main(String[] args) {
        SpringApplication.run(MeetingMessageApplication.class, args);
    }
}
