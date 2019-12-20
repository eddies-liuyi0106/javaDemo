package net.canway.meeting_message;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan("net.canway.meeting_message")
@MapperScan("net.canway.meeting_message.mapper")
public class MeetingMessageApplication {
    public static void main(String[] args) {
        SpringApplication.run(MeetingMessageApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfig(){
        return new WebMvcConfigurer(){
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:8080")
                        .allowCredentials(true)
                        .allowedMethods("POST","GET","DELETE","PUT","PATCH","HEAD","OPTIONS");
            }
        };
    }
}
