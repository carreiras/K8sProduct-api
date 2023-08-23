package carreiras.com.github.k8s.productapi.config;

import java.text.ParseException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import carreiras.com.github.k8s.productapi.service.DbService;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DbConfig {

    private final DbService dbService;

    @Bean
    public boolean instantiateDatabase() throws ParseException {
        dbService.instantiateTestDatabase();
        return true;
    }
}
