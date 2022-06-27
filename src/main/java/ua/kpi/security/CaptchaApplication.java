package ua.kpi.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import ua.kpi.security.console.Runner;

@SpringBootApplication
public class CaptchaApplication {
    public static void main(String[] args) {
        SpringApplication.run(CaptchaApplication.class, args);
        Runner.getInstance().getMenu();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
